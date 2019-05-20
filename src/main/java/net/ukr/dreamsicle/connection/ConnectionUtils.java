package net.ukr.dreamsicle.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;

public class ConnectionUtils {
    final static Logger LOGGER = Logger.getLogger(DBConnection.class);

    public static Connection getConnection() {
        return new DBConnection().getConnection();
    }

    public static void closeQuietly(Connection connection) {
        try {
            getConnection().close();
            connection.close();
        } catch (Exception e) {
            LOGGER.info("closed closeQuietly");
        }
    }

    public static void rollbackQuietly(Connection connection) {
        try {
            getConnection().close();
            connection.rollback();
        } catch (Exception e) {
            LOGGER.info("closed rollbackQuietly");
        }
    }
}
