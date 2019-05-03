package net.ukr.dreamsicle.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void destroy() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
