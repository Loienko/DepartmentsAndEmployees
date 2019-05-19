package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.exception.ApplicationException;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/employee")
public class EmployeeController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employeeList = null;
        HttpSession session = req.getSession();
        boolean check = false;
        String errorCreateNewEmployee = "";
        String errorEditEmployee = "";

        session.setAttribute("errorCreateNewEmployee", errorCreateNewEmployee);
        session.setAttribute("errorEditEmployee", errorEditEmployee);

        String nameDepartFromDepartment = req.getParameter("name_depart");

        if (nameDepartFromDepartment != null) {
            req.setAttribute("nameDepartFromDepartment", nameDepartFromDepartment);
            session.setAttribute("nameDepartFromDepartment", nameDepartFromDepartment);
        } else {
            LOGGER.info("nameDepartFromDepartment is empty");
            nameDepartFromDepartment = (String) session.getAttribute("nameDepartFromDepartment");
        }

        if (!nameDepartFromDepartment.isEmpty()) {
            Connection connection = new DBConnection().getConnection();
            DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
            try {
                if (!connection.isClosed()) {
                    employeeList = dbUtilsDepartment.getEmployeeListFromDepartmentType(connection, nameDepartFromDepartment);
                } else {
                    LOGGER.info("Connection with DB closed");
                    check = true;
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (SQLException e) {
                LOGGER.error(e);
                forwardToPage("error.jsp", req, resp);
                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
            }
        }

        if (!check) {
            req.setAttribute("employeeList", employeeList);
            forwardToPage("employee.jsp", req, resp);
        } else {
            forwardToPage("error.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
