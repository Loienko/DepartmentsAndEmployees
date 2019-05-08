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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/editEmployee")
public class EditEmployeeController extends AbstractServlet {
    String emailEmployeeParameter;
    private ValidEmailAddress validEmailAddress = new ValidEmailAddress();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String errorEditEmployee = (String) session.getAttribute("errorEditEmployee");
        emailEmployeeParameter = req.getParameter("emailEmployee");

        req.setAttribute("errorEditEmployee", errorEditEmployee);
        req.setAttribute("emailEmployeeParameter", emailEmployeeParameter);

        if (emailEmployeeParameter.isEmpty()) {
            forwardToPage("error.jsp", req, resp);
        } else {
            Connection connection = new DBConnection().getConnection();
            DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
            try {
                if (!connection.isClosed()) {
                    Employee employeeForUpdate = dbUtilsEmployee.findEmployeeForUpdate(connection, emailEmployeeParameter);
                    req.setAttribute("employeeForUpdate", employeeForUpdate);
                    forwardToFragment("editEmployee.jsp", req, resp);
                } else {
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (Exception e) {
                e.printStackTrace();
//                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
                forwardToPage("error.jsp", req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String errorDataDepartment = "";
        String updateNameEmployee = req.getParameter("updateNameEmployee");
        String updateSurnameEmployee = req.getParameter("updateSurnameEmployee");
        String updateEmailEmployee = req.getParameter("updateEmailEmployee");
        String updateDateEmployee = req.getParameter("updateDateEmployee");

        boolean validUniqueEmailAddress = validEmailAddress.isValidUniqueEmailAddress(updateEmailEmployee);

        if (!updateDateEmployee.isEmpty() || !updateSurnameEmployee.isEmpty() ||
                !updateEmailEmployee.isEmpty() || !updateDateEmployee.isEmpty()) {
            Connection connection = new DBConnection().getConnection();
            DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
            Employee employeeUpdate = new Employee(updateNameEmployee, updateSurnameEmployee, updateEmailEmployee, getDateFormat(updateDateEmployee));
            try {
                if (!connection.isClosed()) {
                    boolean validEmailByDB = dbUtilsEmployee.isValidEmailByDB(connection, updateEmailEmployee);
                    if (validUniqueEmailAddress && !validEmailByDB) {
                        dbUtilsEmployee.updateEmployee(connection, employeeUpdate, emailEmployeeParameter);
                        resp.sendRedirect(req.getContextPath() + "/employee");
                    } else {
                        session.setAttribute("errorDataDepartment", errorDataDepartment);
                        forwardToFragment("editEmployee.jsp", req, resp);
                    }
                }
            } catch (SQLException e) {
//            throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
                e.printStackTrace();
                forwardToPage("error.jsp", req, resp);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/employee");
        }
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
