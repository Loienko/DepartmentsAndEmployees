package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtilsEmployee {
    public static void main(String[] args) {
        DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
        DBConnection dbConnection = new DBConnection();
        try {
//            dbUtilsEmployee.removeEmployee(dbConnection.getConnection(), "asd");
//            dbUtilsEmployee.addNewEmployee(dbConnection.getConnection(), new Employee(), "Design");
            dbUtilsEmployee.updateEmployee(dbConnection.getConnection(), new Employee("Evil", "Reptile", "annutockha777@gmail.com", "04-05-2019"), "2");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addNewEmployee(Connection connection, Employee employee, String idDepartment) throws SQLException {
        String sqlQuery = "INSERT INTO employee(id_department ,name, surname, email, date) VALUES (?, ?, ?, ?, ?)";

        int anInt = getAnInt(connection, idDepartment);

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

        preparedStatement.setInt(1, anInt);
        preparedStatement.setString(2, employee.getName());
        preparedStatement.setString(3, employee.getSurname());
        preparedStatement.setString(4, employee.getEmail());
        preparedStatement.setString(5, employee.getCreateDate());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private int getAnInt(Connection connection, String idDepartment) throws SQLException {
        String sqlQueryGetIdDepartment = "SELECT id FROM department WHERE name_depart = '" + idDepartment + "'";

        PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQueryGetIdDepartment);
        ResultSet resultSet = preparedStatement1.executeQuery();
        int anInt = 0;
        while (resultSet.next()) {
            anInt = resultSet.getInt(1);
        }
        return anInt;
    }

    public void updateEmployee(Connection connection, Employee employee, String emailEmployeeParameter) throws SQLException {
        String sqlQuery = "Update employee set name = ?, surname= ?, email=?, date = ? WHERE email = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getSurname());
        preparedStatement.setString(3, employee.getEmail());
        preparedStatement.setString(4, employee.getCreateDate());
        preparedStatement.setString(5, emailEmployeeParameter);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void removeEmployee(Connection connection, String remove) throws SQLException {
        String sqlQuery = "DELETE FROM employee WHERE email = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

        preparedStatement.setString(1, remove);
        preparedStatement.executeUpdate();
        connection.close();
    }

    public Employee findEmployeeForUpdate(Connection connection, String emailEmployeeParameter) throws SQLException {
        String sqlQuery = "SELECT name, surname, email, date FROM employee WHERE email = ?";
        Employee employeeList = new Employee();

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

        preparedStatement.setString(1, emailEmployeeParameter);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            employeeList.setName(resultSet.getString("name"));
            employeeList.setSurname(resultSet.getString("surname"));
            employeeList.setEmail(resultSet.getString("email"));
            employeeList.setCreateDate(resultSet.getString("date"));
        }
        return employeeList;
    }
}
