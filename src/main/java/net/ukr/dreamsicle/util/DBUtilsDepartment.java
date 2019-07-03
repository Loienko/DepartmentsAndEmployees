package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtilsDepartment implements Actions {

    public void addNewDepartment(Department department) throws SQLException {
        String sqlQuery = "INSERT INTO department(nameDepart, countEmployee) VALUES (?, ?)";

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, department.getName_depart());
            preparedStatement.setInt(2, department.getCountEmployee());
            preparedStatement.executeUpdate();
        }
    }

    public void updateDepartment(String department, String nameDepartForChange) throws SQLException {
        String sqlQuery = "Update department set nameDepart = ? WHERE nameDepart = ?";

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepartForChange);
            preparedStatement.setString(2, department);

            preparedStatement.executeUpdate();
        }
    }

    public List<Department> getListDepartment() throws SQLException {
        List<Department> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM department ORDER BY nameDepart";


        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            getCountTable();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Department department = new Department();
                    department.setName_depart(resultSet.getString(2));
                    department.setCountEmployee(resultSet.getInt(3));
                    list.add(department);
                }
            }

        }
        return list;
    }

    private void getCountTable() throws SQLException {
        String sql = "UPDATE department SET countEmployee = " +
                "(SELECT COUNT(idDepartment) FROM employee WHERE idDepartment = department.id)";
        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    public List<Employee> getEmployeeListFromDepartmentType(String nameDepartment) throws SQLException {
        String sqlQuery = "SELECT a.name, a.surname, a.email, a.date FROM employee a WHERE idDepartment = " +
                "(SELECT id FROM department WHERE nameDepart = ?) ORDER BY name";

        List<Employee> list = new ArrayList<>();
        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepartment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setName(resultSet.getString("name"));
                    employee.setSurname(resultSet.getString("surname"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setCreateDate(resultSet.getString("date"));
                    list.add(employee);
                }
            }
        }
        return list;
    }

    public int getCountEmployeeFromDepartment(String nameDepart) throws SQLException {
        int anInt = 0;
        String sqlQuery = "SELECT countEmployee FROM department WHERE nameDepart = ?";

        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepart);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    anInt = resultSet.getInt("count_employee");
                }
            }
        }
        return anInt;
    }

    @Override
    public String uniqueParameter(String param) throws SQLException {
        String sqlQuery = "SELECT name_depart FROM department WHERE name_depart = ?";
        try (java.sql.Connection connection = Connection.getConnection()) {
            return getQuery(connection, param, sqlQuery);
        }
    }

    @Override
    public Object findParameterForUpdate(String param) throws SQLException {
        String sqlQuery = "SELECT nameDepart FROM department WHERE nameDepart = ?";
        Department departmentForUpdate = new Department();
        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    departmentForUpdate.setName_depart(resultSet.getString("name_depart"));
                }
            }
        }

        return departmentForUpdate;
    }


    @Override
    public void remove(String parameter) throws SQLException {
        String sqlQuery = "DELETE FROM department WHERE nameDepart = ?";
        try (java.sql.Connection connection = Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, parameter);
            preparedStatement.executeUpdate();
        }
    }
}