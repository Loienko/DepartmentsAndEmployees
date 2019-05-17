package net.ukr.dreamsicle.servlet.servletPage.employee;

import org.junit.Assert;
import org.junit.Test;

public class EditEmployeeControllerTest {

    @Test
    public void getDateFormat() {
        EditEmployeeController controller = new EditEmployeeController();
        String dateFormat = controller.getDateFormat("2015-09-05");
        Assert.assertEquals("05.09.2015", dateFormat);
    }
}