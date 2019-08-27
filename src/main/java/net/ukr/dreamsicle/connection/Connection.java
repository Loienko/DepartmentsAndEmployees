package net.ukr.dreamsicle.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class Connection implements AutoCloseable {
    final static Logger LOGGER = Logger.getLogger(Connection.class);

    public static java.sql.Connection getConnection() {
        Context context;
        java.sql.Connection connection = null;
        try {
            context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/depart");
            connection = dataSource.getConnection();
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
