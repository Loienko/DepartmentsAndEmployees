package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtilsDepartment {

    public void addNewDepartment(Department department) throws SQLException {
        String sqlQuery = "INSERT INTO department(name_depart, count_employee) VALUES (?, ?)";

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, department.getName_depart());
            preparedStatement.setInt(2, department.getCount_employee());
            preparedStatement.executeUpdate();
        }
    }

    public void updateDepartment(String department, String nameDepartForChange) throws SQLException {
        String sqlQuery = "Update department set name_depart = '" + nameDepartForChange + "' WHERE name_depart = '" + department + "'";

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);

            preparedStatement.executeUpdate();
        }
    }

    public void deleteDepartment(String nameDepartForDelete) throws SQLException {
        String sqlQuery = "DELETE FROM department WHERE name_depart = ?";

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepartForDelete);
            preparedStatement.executeUpdate();
        }
    }

    public List<Department> getListDepartment() throws SQLException {
        List<Department> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM department ORDER BY name_depart";
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            getCountTable(connection);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Department department = new Department();
                department.setName_depart(resultSet.getString(2));
                department.setCount_employee(resultSet.getInt(3));
                list.add(department);
            }
        }
        return list;
    }

    private void getCountTable(Connection connection) throws SQLException {
        String sql = "UPDATE department SET count_employee = (SELECT COUNT(id_department) FROM employee WHERE id_department = department.id)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    public List<Employee> getEmployeeListFromDepartmentType(String nameDepartment) throws SQLException {
        String sqlQuery = "SELECT a.name, a.surname, a.email, a.date FROM employee a WHERE id_department = " +
                "(SELECT id FROM department WHERE name_depart = '" + nameDepartment + "') ORDER BY name";

        List<Employee> list = new ArrayList<>();
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setEmail(resultSet.getString("email"));
                employee.setCreateDate(resultSet.getString("date"));
                list.add(employee);
            }
        }
        return list;
    }

    public Department findDepartmentForUpdate(String nameDepart) throws SQLException {
        String sqlQuery = "SELECT name_depart FROM department WHERE name_depart = ?";
        Department departmentForUpdate = new Department();
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepart);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                departmentForUpdate.setName_depart(resultSet.getString("name_depart"));
            }
        }
        return departmentForUpdate;
    }

    public int getCountEmployeeFromDepartment(String nameDepart) throws SQLException {
        int anInt = 0;
        String sqlQuery = "SELECT count_employee FROM department WHERE name_depart = ?";

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepart);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                anInt = resultSet.getInt("count_employee");
            }
        }
        return anInt;
    }

    public String getUniqueDepartmentName(String departUniqueName) throws SQLException {
        String sqlQuery = "SELECT name_depart FROM department WHERE name_depart = ?";
        String string = "";
        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, departUniqueName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                string = resultSet.getString(1);
            }
        }
        return string;
    }
}
