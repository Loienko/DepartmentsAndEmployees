package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.beans.Department;
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

@WebServlet("/editDepartment")
public class EditDepartmentController extends AbstractServlet {
    private String nameDepartParameter;
    private static final Logger LOGGER = Logger.getLogger(EditDepartmentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String errorEditDepartment = (String) session.getAttribute("errorEditDepartment");
        req.setAttribute("errorEditDepartment", errorEditDepartment);

        nameDepartParameter = req.getParameter("nameDepart");
        req.setAttribute("nameDepartParameter", nameDepartParameter);

        if (nameDepartParameter.isEmpty()) {
            forwardToPage("error.jsp", req, resp);
        } else {
            Connection connection = new DBConnection().getConnection();
            DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
            try {
                if (!connection.isClosed()) {
                    Department departmentForUpdate = dbUtilsDepartment.findDepartmentForUpdate(connection, nameDepartParameter);
                    req.setAttribute("departmentForUpdate", departmentForUpdate);
                    forwardToFragment("editDepartment.jsp", req, resp);
                } else {
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (SQLException e) {
                LOGGER.error(e);
                forwardToPage("error.jsp", req, resp);
                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String nameUpdateDepartment = req.getParameter("nameUpdateDepartment");

        Connection connection = new DBConnection().getConnection();
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();

        String uniqueDepartmentName = null;
        try {
            uniqueDepartmentName = dbUtilsDepartment.getUniqueDepartmentName(connection, nameUpdateDepartment);
        } catch (SQLException e) {
            LOGGER.error(e);
            forwardToPage("error.jsp", req, resp);
//            /throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
        }

        if (!nameUpdateDepartment.isEmpty() && uniqueDepartmentName.isEmpty()) {
            try {
                if (!connection.isClosed()) {
                    dbUtilsDepartment.updateDepartment(connection, nameDepartParameter, nameUpdateDepartment);
                    resp.sendRedirect(req.getContextPath() + "/department");
                } else {
                    LOGGER.info("Connection with DB closed");
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (SQLException e) {
                LOGGER.error(e);
                forwardToPage("error.jsp", req, resp);
            }
        } else {
            LOGGER.info("nameUpdateDepartment not input");
            resp.sendRedirect(req.getContextPath() + "/department");
        }
    }
}
