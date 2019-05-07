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

@WebServlet("/addNewEmployee")
public class AddNewEmployeeController extends AbstractServlet {

    private ValidEmailAddress validEmailAddress = new ValidEmailAddress();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameterNameDepartment = req.getParameter("nameDepartment");
        HttpSession session = req.getSession();
        session.setAttribute("parameterNameDepartment", parameterNameDepartment);
        String errorDataEmployee = (String) session.getAttribute("errorData");
        req.setAttribute("errorDataEmployee", errorDataEmployee);
        forwardToFragment("add_new_employee.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        boolean hasError = false;
        String errorDataEmployee = "";
        String errorEmail = "";
        Employee employeeAddNewEmployee = null;

        String parameterNameDepartment = (String) req.getSession().getAttribute("parameterNameDepartment");
        String nameEmployee = req.getParameter("nameEmployee");
        String surnameEmployee = req.getParameter("surnameEmployee");
        String emailEmployee = req.getParameter("emailEmployee");
        String dateEmployee = req.getParameter("dateEmployee");

        /*if (validEmailAddress.isValidUniqueEmailAddress(emailEmployee)) {
            errorEmail = "";
        } else {
            errorEmail = "regular";
        }*/

        if (nameEmployee.isEmpty() || surnameEmployee.isEmpty() || emailEmployee.isEmpty()) {
            hasError = true;
            errorDataEmployee = "Please Input correct data (name, surname, email) !!!";
        } else {
            DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
            Connection connection = new DBConnection().getConnection();
            if (dateEmployee.isEmpty()){

            }
            employeeAddNewEmployee = new Employee(nameEmployee, surnameEmployee, emailEmployee, getDateFormat(dateEmployee));
            try {
                if (!connection.isClosed()) {
                    dbUtilsEmployee.addNewEmployee(connection, employeeAddNewEmployee, parameterNameDepartment);
                } else {
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (SQLException e) {
                hasError = true;
                e.printStackTrace();
                forwardToPage("error.jsp", req, resp);
//                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
            }
        }

        if (hasError) {
            session.setAttribute("errorDataEmployee", errorDataEmployee);
            forwardToFragment("add_new_employee.jsp", req, resp);
        } else {
            req.setAttribute("employeeAddNewEmployee", employeeAddNewEmployee);
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
