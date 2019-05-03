package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsEmployee;

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameterNameDepartment = req.getParameter("nameDepartment");
        HttpSession session = req.getSession();
        session.setAttribute("parameterNameDepartment", parameterNameDepartment);
        forwardToFragment("add_new_employee.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
        Connection connection = new DBConnection().getConnection();

        String parameterNameDepartment = (String) req.getSession().getAttribute("parameterNameDepartment");
        String nameEmployee = req.getParameter("nameEmployee");
        String surnameEmployee = req.getParameter("surnameEmployee");
        String emailEmployee = req.getParameter("emailEmployee");
        String dateEmployee = req.getParameter("dateEmployee");


        Employee employeeAddNewEmployee = new Employee(nameEmployee, surnameEmployee, emailEmployee, dateEmployee);

        try {
            dbUtilsEmployee.addNewEmployee(connection, employeeAddNewEmployee, parameterNameDepartment);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("employeeAddNewEmployee", employeeAddNewEmployee);

//        forwardToPage("department.jsp", req, resp);
        resp.sendRedirect(req.getContextPath() + "/employee");
    }
}
