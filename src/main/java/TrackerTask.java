import Config.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.rabbitmq.client.*;
import db.DBConnection;
import entities.TrackerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by fiyyanp on 11/7/2016.
 */
public class TrackerTask {
    private static final Logger LOG = LoggerFactory.getLogger(TrackerTask.class);
    private static final String EXCHANGE_NAME = "";
    private static final String QUEUE = "Messages.TextMessage:Messages_test";
    private AppConfig config;
    private Channel ch;
    private PreparedStatement preparedStatement;
    private SimpleDateFormat df;


    public TrackerTask(AppConfig configuration) {
        this.config = configuration;
    }

    private ConnectionFactory createConnectionFactory(AppConfig configuration) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(configuration.getRabbitMQUsername());
        factory.setPassword(configuration.getRabbitMQPassword());
        factory.setHost(configuration.getRabbitMQHost());
        factory.setVirtualHost("/"+configuration.getRabbitMQVHost());
        return factory;
    }

    public void execute() {
        try {
            ch = createConnectionFactory(config).newConnection().createChannel();
            //ch.exchangeDeclare(EXCHANGE_NAME, "topic", true);
            //QUEUE = ch.queueDeclare("Messages.TextMessage:Messages_test", true, false, false, null).getQueue();

            System.out.println(" [*] Waiting for message. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(ch){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    LOG.info(" [x] Received message : " + envelope.getRoutingKey() + " : " + message);

                    //insert data to table
                    insertToDB(message);
                }
            };
            ch.basicConsume(QUEUE, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.info("Something went wrong: "+e.getMessage());
        }
    }

    private void insertToDB(String message) {
        ObjectMapper mapper = new ObjectMapper();
        TrackerData data = null;
        try {
            //mapping data
            data = mapper.readValue(message, TrackerData.class);
        } catch (IOException e) {
            LOG.error("Json format error");
        }

        //insert

        java.sql.Connection conn = DBConnection.makeJDBCConnection(config.getMySqlhost(), config.getMySqlDb(), config.getMySqluser(), config.getMySqlpass());
        if(conn != null){
            String query = "INSERT INTO tb_tracker (MAC_ID, DEVICE_NAME, LONGITUDE, LATITUDE, SPEED, DATETIME) " +
                    "VALUES (?,?,?,?,?,?) " +
                    "ON DUPLICATE KEY UPDATE" +
                    "  LONGITUDE=VALUES(LONGITUDE)," +
                    "  LATITUDE=VALUES(LATITUDE)," +
                    "  SPEED=VALUES(SPEED)," +
                    "  DATETIME=VALUES(DATETIME)";
            try {
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, data.getmAC());
                preparedStatement.setString(2, data.getmAC());
                preparedStatement.setDouble(3, data.getData().get(0));
                preparedStatement.setDouble(4, data.getData().get(1));
                preparedStatement.setDouble(5, data.getSpeed());
                preparedStatement.setString(6, (df.format(new Date())));

                if(preparedStatement.executeUpdate() > 0) LOG.info("Inserting success: "+message);
                else LOG.info("Inserting done");

            } catch (SQLException e) {
                LOG.error("Something went wrong: "+e.getMessage());
            }
        }else{
            LOG.info("DB Connection: null");
        }
    }
}
