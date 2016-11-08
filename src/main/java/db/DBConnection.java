package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by fiyyanp on 11/7/2016.
 */
public class DBConnection {
    private static final Logger LOG = LoggerFactory.getLogger(DBConnection.class);
    private static Connection connection;

    public static Connection makeJDBCConnection(String DB_URL, String DB_NAME, String DB_USER, String DB_PASS) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            LOG.info("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            LOG.info("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return connection;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            connection = DriverManager.getConnection(DB_URL+"/"+DB_NAME, DB_USER, DB_PASS);
            if (connection != null) {
                LOG.info("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                LOG.info("Failed to make connection!");
            }
            return connection;
        } catch (SQLException e) {
            LOG.info("MySQL Connection Failed!");
            e.printStackTrace();
            return connection;
        }
    }
}
