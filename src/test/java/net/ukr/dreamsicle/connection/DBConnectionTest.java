package net.ukr.dreamsicle.connection;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {

    @Test
    public void getConnection() {
        Connection connection = new DBConnection().getConnection();

        try {
            boolean closed = !connection.isClosed();
            Assert.assertTrue(closed);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}