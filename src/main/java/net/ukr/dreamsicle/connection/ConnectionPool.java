package net.ukr.dreamsicle.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool implements AutoCloseable {
    final static Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance = null;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            LOGGER.info("Create connection");
        }
        return instance;
    }

    public Connection getConnection() {
        Context context;
        Connection connection = null;
        try {
            context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/depart");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            LOGGER.error(e);
        }
        return connection;
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("connection is close");
    }
}
