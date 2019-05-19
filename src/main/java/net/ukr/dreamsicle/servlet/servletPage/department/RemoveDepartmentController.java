package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.exception.ApplicationException;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/removeDepartment")
public class RemoveDepartmentController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(RemoveDepartmentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("nameDepart");

        Connection connection = new DBConnection().getConnection();
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();

        if (!delete.isEmpty()) {
            try {
                if (!connection.isClosed()) {
                    int countEmployeeFromDepartment = dbUtilsDepartment.getCountEmployeeFromDepartment(connection, delete);
                    if (countEmployeeFromDepartment <= 0) {
                        dbUtilsDepartment.deleteDepartment(connection, delete);
                        resp.sendRedirect(req.getContextPath() + "/department");
                    } else {
                        LOGGER.info("count employee from department not null");
                        forwardToFragment("removeDepartment.jsp", req, resp);
                    }
                } else {
                    LOGGER.info("Connection with DB closed");
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (SQLException e) {
                LOGGER.error(e);
                forwardToPage("error.jsp", req, resp);
                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
            }
        } else {
            LOGGER.info("Data parameter 'delete' is empty");
            resp.sendRedirect(req.getContextPath() + "/department");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
