package net.ukr.dreamsicle.servlet.servletPage.employee;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddNewEmployeeControllerTest {
    private static AddNewEmployeeController controller;

    @BeforeClass
    public static void init() {
        controller = new AddNewEmployeeController();
    }

    @Test
    public void doGet() {
    }

    @Test
    public void doPost() {
    }

    @Test
    public void getDateFormat() {
        String dateFormat = controller.getDateFormat("2015-15-05");
        Assert.assertEquals("05.15.2015", dateFormat);
    }
}