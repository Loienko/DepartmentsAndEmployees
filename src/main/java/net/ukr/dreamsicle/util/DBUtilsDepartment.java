package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtilsDepartment {
    private static final Logger LOGGER = Logger.getLogger(DBUtilsDepartment.class);

    public void addNewDepartment(Connection connection, Department department) throws SQLException {
        String sqlQuery = "INSERT INTO department(name_depart, count_employee) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, department.getName_depart());
            preparedStatement.setInt(2, department.getCount_employee());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        connection.close();

    }

    public void updateDepartment(Connection connection, String department, String nameDepartForChange) throws SQLException {
        String sqlQuery = "Update department set name_depart = '" + nameDepartForChange + "' WHERE name_depart = '" + department + "'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        connection.close();
    }

    public void deleteDepartment(Connection connection, String nameDepartForDelete) throws SQLException {
        String sqlQuery = "DELETE FROM department WHERE name_depart = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepartForDelete);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        connection.close();
    }

    public List<Department> getListDepartment(Connection connection) throws SQLException {
        List<Department> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM department ORDER BY name_depart";

        try (Connection connection1 = new DBConnection().getConnection();
             PreparedStatement preparedStatement = connection1.prepareStatement(sqlQuery)) {
            getCountTable(connection1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Department department = new Department();
                department.setName_depart(resultSet.getString(2));
                department.setCount_employee(resultSet.getInt(3));
                list.add(department);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }

//        connection.close();

        return list;
    }

    private void getCountTable(Connection connection) throws SQLException {
        String sql = "UPDATE department SET count_employee = (SELECT COUNT(id_department) FROM employee WHERE id_department = department.id)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public List<Employee> getEmployeeListFromDepartmentType(Connection connection, String nameDepartment) throws SQLException {
        String sqlQuery = "SELECT a.name, a.surname, a.email, a.date FROM employee a WHERE id_department = " +
                "(SELECT id FROM department WHERE name_depart = '" + nameDepartment + "') ORDER BY name";
        List<Employee> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setEmail(resultSet.getString("email"));
                employee.setCreateDate(resultSet.getString("date"));
                list.add(employee);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }

        connection.close();
        return list;
    }

    public Department findDepartmentForUpdate(Connection connection, String nameDepart) throws SQLException {
        String sqlQuery = "SELECT name_depart FROM department WHERE name_depart = ?";
        Department departmentForUpdate = new Department();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepart);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                departmentForUpdate.setName_depart(resultSet.getString("name_depart"));
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        connection.close();
        return departmentForUpdate;
    }

    public int getCountEmployeeFromDepartment(Connection connection, String nameDepart) throws SQLException {
        int anInt = 0;
        String sqlQuery = "SELECT count_employee FROM department WHERE name_depart = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nameDepart);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                anInt = resultSet.getInt("count_employee");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return anInt;
    }

    public String getUniqueDepartmentName(Connection connection, String departUniqueName) throws SQLException {
        String sqlQuery = "SELECT name_depart FROM department WHERE name_depart = ?";
        String string = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, departUniqueName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                string = resultSet.getString(1);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return string;
    }
}
