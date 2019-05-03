package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsEmployee;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        emailEmployeeParameter = req.getParameter("emailEmployee");
        req.setAttribute("emailEmployeeParameter", emailEmployeeParameter);
        Connection connection = new DBConnection().getConnection();
        DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
        Employee employeeForUpdate = null;

        try {
            employeeForUpdate = dbUtilsEmployee.findEmployeeForUpdate(connection, emailEmployeeParameter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("employeeForUpdate", employeeForUpdate);
        forwardToFragment("editEmployee.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String updateNameEmployee = req.getParameter("updateNameEmployee");
        String updateSurnameEmployee = req.getParameter("updateSurnameEmployee");
        String updateEmailEmployee = req.getParameter("updateEmailEmployee");

        Connection connection = new DBConnection().getConnection();
        DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
        Employee employeeUpdate = new Employee(updateNameEmployee, updateSurnameEmployee, updateEmailEmployee, "");

        try {
            dbUtilsEmployee.updateEmployee(connection, employeeUpdate, emailEmployeeParameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/employee");
    }
}
