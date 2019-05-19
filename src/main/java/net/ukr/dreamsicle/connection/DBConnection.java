package net.ukr.dreamsicle.connection;

import net.ukr.dreamsicle.exception.ApplicationException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    final static Logger LOGGER = Logger.getLogger(DBConnection.class);
    private static ReadDataFromResFileAppProp readDataFromResFileAppProp;

    public DBConnection() {
        readDataFromResFileAppProp = new ReadDataFromResFileAppProp();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(readDataFromResFileAppProp.getProperties("db.driver"));
            connection = DriverManager.getConnection(
                    readDataFromResFileAppProp.getProperties("db.url"),
                    readDataFromResFileAppProp.getProperties("db.username"),
                    readDataFromResFileAppProp.getProperties("db.password"));
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e);
//            throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
        }
        return connection;
    }
}
