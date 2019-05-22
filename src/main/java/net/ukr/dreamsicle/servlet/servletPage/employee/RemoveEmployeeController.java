package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.exception.ApplicationException;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsEmployee;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/removeEmployee")
public class RemoveEmployeeController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(RemoveEmployeeController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("emailEmployee");
        if (!delete.isEmpty()) {
            DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
            try {
                dbUtilsEmployee.removeEmployee(delete);
                resp.sendRedirect(req.getContextPath() + "/employee");

            } catch (SQLException | ApplicationException e) {
                LOGGER.error("error", e);
                forwardToPage("error.jsp", req, resp);
            }
        } else {
            LOGGER.info("Field delete is empty");
            resp.sendRedirect(req.getContextPath() + "/employee");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
