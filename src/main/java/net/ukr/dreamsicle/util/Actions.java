package net.ukr.dreamsicle.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Actions<T> {

    void remove(String parameter) throws SQLException;

    String uniqueParameter(String param) throws SQLException;

    T findParameterForUpdate(String param) throws SQLException;

    default String getQuery(Connection connection, String param, String sqlQuery) throws SQLException {
        String result = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getString(1);
                }
            }
        }
        return result;
    }
}