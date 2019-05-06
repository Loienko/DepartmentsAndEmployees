package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsEmployee;
import net.ukr.dreamsicle.validation.ValidEmailAddress;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/editEmployee")
public class EditEmployeeController extends AbstractServlet {
    String emailEmployeeParameter;
    private ValidEmailAddress validEmailAddress = new ValidEmailAddress();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        emailEmployeeParameter = req.getParameter("emailEmployee");
        req.setAttribute("emailEmployeeParameter", emailEmployeeParameter);
        Connection connection = new DBConnection().getConnection();
        DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
        Employee employeeForUpdate = null;

        try {
            employeeForUpdate = dbUtilsEmployee.findEmployeeForUpdate(connection, emailEmployeeParameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("employeeForUpdate", employeeForUpdate);
        forwardToFragment("editEmployee.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String updateNameEmployee = req.getParameter("updateNameEmployee");
        String updateSurnameEmployee = req.getParameter("updateSurnameEmployee");
        String updateEmailEmployee = req.getParameter("updateEmailEmployee");
        String updateDateEmployee = req.getParameter("updateDateEmployee");

        /*if (validEmailAddress.isValidEmailAddress(updateEmailEmployee)) {

        } else {

        }*/

        Connection connection = new DBConnection().getConnection();
        DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
        Employee employeeUpdate = new Employee(updateNameEmployee, updateSurnameEmployee, updateEmailEmployee, getDateFormat(updateDateEmployee));

        try {
            dbUtilsEmployee.updateEmployee(connection, employeeUpdate, emailEmployeeParameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/employee");
    }

    private String getDateFormat(String dateEmployee) {
        String[] split = dateEmployee.split("-");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = split.length - 1; i >= 0; i--) {
            stringBuffer.append(split[i]).append(".");
        }
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }
}
