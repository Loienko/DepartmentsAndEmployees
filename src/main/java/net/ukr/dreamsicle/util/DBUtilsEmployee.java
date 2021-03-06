package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBUtilsEmployee implements Actions {

    public void addNewEmployee(Employee employee, String idDepartment) throws SQLException {
        String sqlQuery = "INSERT INTO employee(idDepartment ,name, surname, email, date) VALUES (?, ?, ?, ?, ?)";

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            int anInt = getDepartmentIdByName(idDepartment);
            preparedStatement.setInt(1, anInt);
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getSurname());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getCreateDate());
            preparedStatement.executeUpdate();
        }
    }

    private int getDepartmentIdByName(String idDepartment) throws SQLException {
        String sqlQueryGetIdDepartment = "SELECT id FROM department WHERE nameDepart = ?";
        int anInt = 0;

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQueryGetIdDepartment)) {
            preparedStatement.setString(1, idDepartment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    anInt = resultSet.getInt(1);
                }
            }
        }
        return anInt;
    }

    public void updateEmployee(String substring, List arrayListValueField, String emailEmployeeParameter) throws SQLException {
        String sqlQuery = "Update employee set " + substring + " WHERE email = ?";

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            for (int i = 0; i < arrayListValueField.size(); i++) {
                preparedStatement.setString(i + 1, (String) arrayListValueField.get(i));
            }
            preparedStatement.setString(arrayListValueField.size() + 1, emailEmployeeParameter);
            preparedStatement.executeUpdate();
        }
    }

    public boolean isValidEmailByDB(String emailForCheck) throws SQLException {
        boolean aBoolean = false;
        String sqlQuery = "SELECT count(email) FROM employee WHERE email = ?";

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, emailForCheck);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    aBoolean = resultSet.getBoolean(1);
                }
            }
        }
        return aBoolean;
    }

    @Override
    public void remove(String parameter) throws SQLException {
        String sqlQuery = "DELETE FROM employee WHERE email = ?";
        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, parameter);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public String uniqueParameter(String param) throws SQLException {
        String sqlQuery = "SELECT email FROM employee WHERE EXISTS(SELECT * FROM employee WHERE email = ?)";
        try (java.sql.Connection connection = Connection.getConnection()) {
            return getQuery(connection, param, sqlQuery);
        }
    }

    @Override
    public Object findParameterForUpdate(String param) throws SQLException {
        String sqlQuery = "SELECT name, surname, email, date FROM employee WHERE email = ?";
        Employee employeeList = new Employee();

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    employeeList.setName(resultSet.getString("name"));
                    employeeList.setSurname(resultSet.getString("surname"));
                    employeeList.setEmail(resultSet.getString("email"));
                    employeeList.setCreateDate(resultSet.getString("date"));
                }
            }
        }
        return employeeList;
    }
}