package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtilsDepartment {
    @Test
    public static void main(String[] args) {
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
        DBConnection dbConnection = new DBConnection();
        try {
//            dbUtilsDepartment.addNewDepartment(dbConnection.getConnection(), new Department(0, "project", 0)); // 200
//            dbUtilsDepartment.updateDepartment(dbConnection.getConnection(), "duuuurb", "durb"); // 200
//            dbUtilsDepartment.deleteDepartment(dbConnection.getConnection(), "sldgnhdggd"); // 200
//            List<Department> listDepartment = dbUtilsDepartment.getListDepartment(dbConnection.getConnection());// 200
//            List<Employee> employeeListFromDepartmentType = dbUtilsDepartment.getEmployeeListFromDepartmentType(dbConnection.getConnection(), "Design");// 200
//
//            /*for (Department department : listDepartment) {
//                System.out.println(department.toString());
//            }*/
//            for (Employee employee : employeeListFromDepartmentType) {
//                System.out.println(employee.toString());
//            }
            String anna = dbUtilsDepartment.getUniqueDepartmentName(dbConnection.getConnection(), "asd");
            if (!anna.isEmpty()) {
                System.out.println("not unique " + anna);
            } else {
                System.out.println("unique");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewDepartment(Connection connection, Department department) throws SQLException {
        String sqlQuery = "INSERT INTO department(name_depart, count_employee) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, department.getName_depart());
        preparedStatement.setInt(2, department.getCount_employee());
        preparedStatement.executeUpdate();
        connection.close();
    }

    public void updateDepartment(Connection connection, String department, String nameDepartForChange) throws SQLException {
        String sqlQuery = "Update department set name_depart = '" + nameDepartForChange + "' WHERE name_depart = '" + department + "'";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlQuery);

        preparedStatement.executeUpdate();
        connection.close();
    }

    public void deleteDepartment(Connection connection, String nameDepartForDelete) throws SQLException {
        String sqlQuery = "DELETE FROM department WHERE name_depart = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, nameDepartForDelete);
        preparedStatement.executeUpdate();
        connection.close();
    }

    public List<Department> getListDepartment(Connection connection) throws SQLException {
        List<Department> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM department ORDER BY name_depart";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        getCountTable(connection);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Department department = new Department();
            department.setName_depart(resultSet.getString(2));
            department.setCount_employee(resultSet.getInt(3));
            list.add(department);
        }
        connection.close();

        return list;
    }

    private void getCountTable(Connection connection) throws SQLException {
        String sql = "UPDATE department SET count_employee = (SELECT COUNT(id_department) FROM employee WHERE id_department = department.id)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    public List<Employee> getEmployeeListFromDepartmentType(Connection connection, String nameDepartment) throws SQLException {
        String sqlQuery = "SELECT a.name, a.surname, a.email, a.date FROM employee a WHERE id_department = " +
                "(SELECT id FROM department WHERE name_depart = '" + nameDepartment + "') ORDER BY name";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Employee> list = new ArrayList<>();

        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setName(resultSet.getString("name"));
            employee.setSurname(resultSet.getString("surname"));
            employee.setEmail(resultSet.getString("email"));
            employee.setCreateDate(resultSet.getString("date"));
            list.add(employee);
        }

        connection.close();
        return list;
    }

    public Department findDepartmentForUpdate(Connection connection, String nameDepart) throws SQLException {
        String sqlQuery = "SELECT name_depart FROM department WHERE name_depart = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, nameDepart);
        ResultSet resultSet = preparedStatement.executeQuery();
        Department departmentForUpdate = new Department();

        while (resultSet.next()) {
            departmentForUpdate.setName_depart(resultSet.getString("name_depart"));
        }
        connection.close();
        return departmentForUpdate;
    }

    public int getCountEmployeeFromDepartment(Connection connection, String nameDepart) throws SQLException {
        int anInt = 0;
        String sqlQuery = "SELECT count_employee FROM department WHERE name_depart = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, nameDepart);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            anInt = resultSet.getInt("count_employee");
        }
        return anInt;
    }

    public String getUniqueDepartmentName(Connection connection, String departUniqueName) throws SQLException {
        String sqlQuery = "SELECT name_depart FROM department WHERE name_depart = ?";
        String string = "";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, departUniqueName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            string = resultSet.getString(1);
        }
        return string;
    }


}
