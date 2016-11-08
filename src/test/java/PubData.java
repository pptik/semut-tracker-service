import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by fiyyanp on 11/7/2016.
 */
public class PubData {
    private static final String EXCHANGE_NAME = "Messages.TextMessage:Messages";
    private static final String HOST = "167.205.7.229";
    private static final String User = "GPSTracker";
    private static final String Password = "GPSTracker";
    private static final String VHOST = "/GPSTracker";
    private static Channel ch;

    public static void main(String[] argv) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(User);
        factory.setPassword(Password);
        factory.setVirtualHost(VHOST);

        Connection connection = factory.newConnection();
        ch = connection.createChannel();
        ch.exchangeDeclare(EXCHANGE_NAME, "topic", true);
        String message = "{\"MAC\":\"18:FE:34:E1:48:B6\",\"Speed\":0.00,\"date\":\"00/00/2000\",\"time\":\"00:00:00:00\",\"data\":[0.000000,0.000000]}";
        ch.basicPublish(EXCHANGE_NAME, "GPSTracker", null, message.getBytes());
        System.out.println(" [x] Sent message : " + message);
    }

}
