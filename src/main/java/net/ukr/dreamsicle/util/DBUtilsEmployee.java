package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBUtilsEmployee {
    private static final Logger LOGGER = Logger.getLogger(DBUtilsEmployee.class);

    public DBUtilsEmployee() {
    }

    public void addNewEmployee(Employee employee, String idDepartment) throws SQLException {
        String sqlQuery = "INSERT INTO employee(id_department ,name, surname, email, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            int anInt = getAnInt(connection, idDepartment);
            preparedStatement.setInt(1, anInt);
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getSurname());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setString(5, employee.getCreateDate());
            preparedStatement.executeUpdate();
        }
    }

    private int getAnInt(Connection connection, String idDepartment) throws SQLException {
        String sqlQueryGetIdDepartment = "SELECT id FROM department WHERE name_depart = '" + idDepartment + "'";
        int anInt = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQueryGetIdDepartment)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                anInt = resultSet.getInt(1);
            }
        }
        return anInt;
    }

    public void updateEmployee(String substring, List arrayListValueField, String emailEmployeeParameter) throws SQLException {
        String sqlQuery = "Update employee set " + substring + " WHERE email = ?";

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            for (int i = 0; i < arrayListValueField.size(); i++) {
                preparedStatement.setString(i + 1, (String) arrayListValueField.get(i));
            }
            preparedStatement.setString(arrayListValueField.size() + 1, emailEmployeeParameter);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public void removeEmployee(String remove) throws SQLException {
        String sqlQuery = "DELETE FROM employee WHERE email = ?";
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, remove);
            preparedStatement.executeUpdate();
        }
    }

    public Employee findEmployeeForUpdate(String emailEmployeeParameter) throws SQLException {
        String sqlQuery = "SELECT name, surname, email, date FROM employee WHERE email = ?";
        Employee employeeList = new Employee();

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, emailEmployeeParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeList.setName(resultSet.getString("name"));
                employeeList.setSurname(resultSet.getString("surname"));
                employeeList.setEmail(resultSet.getString("email"));
                employeeList.setCreateDate(resultSet.getString("date"));
            }
        }
        return employeeList;
    }

    public boolean isValidEmailByDB(String emailForCheck) throws SQLException {
        boolean aBoolean = false;
        String sqlQuery = "SELECT count(email) FROM employee WHERE email = '" + emailForCheck + "'";

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                aBoolean = resultSet.getBoolean(1);
            }
        }
        return aBoolean;
    }
}
