package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.exception.ApplicationException;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/removeDepartment")
public class RemoveDepartmentController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(RemoveDepartmentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("nameDepart");
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();

        String uniqueParameter = "";
        try {
            uniqueParameter = dbUtilsDepartment.uniqueParameter(delete);
        } catch (SQLException e) {
            LOGGER.error("error", e);
            forwardToPage("error.jsp", req, resp);
        }

        if (!delete.isEmpty() && uniqueParameter.length() != 0) {
            try {
                int countEmployeeFromDepartment = dbUtilsDepartment.getCountEmployeeFromDepartment(delete);
                if (countEmployeeFromDepartment <= 0) {
                    dbUtilsDepartment.remove(delete);
                    resp.sendRedirect(req.getContextPath() + "/department");
                } else {
                    LOGGER.info("count employee from department not null");
                    forwardToFragment("removeDepartment.jsp", req, resp);
                }
            } catch (SQLException | ApplicationException e) {
                LOGGER.error("error", e);
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
