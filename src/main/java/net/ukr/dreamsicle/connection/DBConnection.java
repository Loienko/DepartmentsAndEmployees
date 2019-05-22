package net.ukr.dreamsicle.connection;

import net.ukr.dreamsicle.exception.ApplicationException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements AutoCloseable {
    final static Logger LOGGER = Logger.getLogger(DBConnection.class);
    private static ReadDataFromResFileAppProp readDataFromResFileAppProp;
    Connection connection = null;

    public DBConnection() {
        readDataFromResFileAppProp = new ReadDataFromResFileAppProp();
    }

    public Connection getConnection() {
        try {
            Class.forName(readDataFromResFileAppProp.getProperties("db.driver"));
            connection = DriverManager.getConnection(
                    readDataFromResFileAppProp.getProperties("db.url"),
                    readDataFromResFileAppProp.getProperties("db.username"),
                    readDataFromResFileAppProp.getProperties("db.password"));
        } catch (ApplicationException | SQLException | ClassNotFoundException e) {
            LOGGER.error("error", e);
        }
        return connection;
    }

    @Override
    public void close() {
        LOGGER.info("connection closed");
    }
}
