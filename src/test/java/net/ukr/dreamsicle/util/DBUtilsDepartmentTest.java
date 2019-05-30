package net.ukr.dreamsicle.util;

import org.junit.Test;

import java.sql.SQLException;

public class DBUtilsDepartmentTest {
    DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();

    @Test
    public void addNewDepartment() {
    }

    @Test
    public void updateDepartment() {
    }

    @Test
    public void deleteDepartment() {
    }

    @Test
    public void getListDepartment() {
    }

    @Test
    public void getEmployeeListFromDepartmentType() {
    }

    @Test
    public void findDepartmentForUpdate() {
    }

    @Test
    public void getCountEmployeeFromDepartment() {
    }

    @Test
    public void getUniqueDepartmentName() {

    }

    @Test
    public void uniqueParameter() {
        try {
            String s = dbUtilsDepartment.uniqueParameter("Annsqq1");
            System.out.println(s + ", " + s.length());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}