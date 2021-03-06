package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.beans.Department;
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

@WebServlet("/department")
public class DepartmentController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(DepartmentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String errorDataDepartment = "";
        session.setAttribute("errorDataDepartment", errorDataDepartment);

        try {
            List<Department> departmentList = new DBUtilsDepartment().getListDepartment();
            req.setAttribute("depart", departmentList);
            forwardToPage("department.jsp", req, resp);
        } catch (ApplicationException | SQLException e) {
            LOGGER.error("error", e);
            forwardToPage("error.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
