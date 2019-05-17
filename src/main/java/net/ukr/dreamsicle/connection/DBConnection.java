package net.ukr.dreamsicle.connection;

import net.ukr.dreamsicle.exception.ApplicationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static ReadDataFromResFileAppProp readDataFromResFileAppProp;

    public DBConnection() {
        readDataFromResFileAppProp = new ReadDataFromResFileAppProp();
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            Class.forName(readDataFromResFileAppProp.getProperties("db.driver"));
            connection = DriverManager.getConnection(
                    readDataFromResFileAppProp.getProperties("db.url"),
                    readDataFromResFileAppProp.getProperties("db.username"),
                    readDataFromResFileAppProp.getProperties("db.password"));
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
        }
        return connection;
    }

    public static void closeQuietly(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }
}
