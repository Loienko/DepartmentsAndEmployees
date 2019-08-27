package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.beans.Employee;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/employee")
public class EmployeeController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
        HttpSession session = req.getSession();

        boolean check = false;
        List<Employee> employeeList = null;
        String errorCreateNewEmployee = "";
        String errorEditEmployee = "";
        String uniqueDepartmentName = "";

        session.setAttribute("errorCreateNewEmployee", errorCreateNewEmployee);
        session.setAttribute("errorEditEmployee", errorEditEmployee);

        String nameDepartFromDepartment = req.getParameter("name_depart");

        /**
         * Check the name after click button 'Cancel' from AddNewEmployee or EditEmployee
         */
        if (nameDepartFromDepartment == null || nameDepartFromDepartment.length() == 0) {
            nameDepartFromDepartment = (String) session.getAttribute("nameDepartFromDepartment");
        }

        /**
         * Check that request parameter not modification
         */
        try {
            uniqueDepartmentName = dbUtilsDepartment.uniqueParameter(nameDepartFromDepartment);
        } catch (SQLException | ApplicationException e) {
            LOGGER.error(e);
            forwardToPage("error.jsp", req, resp);
        }

        if (nameDepartFromDepartment != null && uniqueDepartmentName.length() != 0) {
            req.setAttribute("nameDepartFromDepartment", nameDepartFromDepartment);
            session.setAttribute("nameDepartFromDepartment", nameDepartFromDepartment);
        } else {
            check = true;
            LOGGER.info("nameDepartFromDepartment is empty");
            nameDepartFromDepartment = (String) session.getAttribute("nameDepartFromDepartment");
        }

        if (!nameDepartFromDepartment.isEmpty() || uniqueDepartmentName.length() != 0) {
            try {
                employeeList = dbUtilsDepartment.getEmployeeListFromDepartmentType(nameDepartFromDepartment);
            } catch (SQLException | ApplicationException e) {
                LOGGER.error("error", e);
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
