package net.ukr.dreamsicle.connection;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void getConnection() {
        /*Connection outerConnection = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            outerConnection = connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (outerConnection.isClosed()) {
                System.out.println("Ok");
            } else {
                System.out.println("not Ok");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void close() {
    }
}