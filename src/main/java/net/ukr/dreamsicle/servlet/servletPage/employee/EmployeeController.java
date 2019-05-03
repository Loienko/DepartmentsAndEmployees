package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/employee")
public class EmployeeController extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employeeList = null;

        DBConnection dbConnection = new DBConnection();
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();

        HttpSession session = req.getSession();

        String nameDepartFromDepartment = req.getParameter("name_depart");
        if (nameDepartFromDepartment != null) {
            req.setAttribute("nameDepartFromDepartment", nameDepartFromDepartment);
            session.setAttribute("nameDepartFromDepartment", nameDepartFromDepartment);
        } else {
            nameDepartFromDepartment = (String) session.getAttribute("nameDepartFromDepartment");
        }

        try {
            employeeList = dbUtilsDepartment.getEmployeeListFromDepartmentType(dbConnection.getConnection(), nameDepartFromDepartment);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("employeeList", employeeList);
        forwardToPage("employee.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
