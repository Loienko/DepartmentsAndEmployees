package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsEmployee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/removeEmployee")
public class RemoveEmployeeController extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("emailEmployee");

        DBConnection dbConnection = new DBConnection();
        DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();

        String errorString = null;
        try {
            dbUtilsEmployee.removeEmployee(dbConnection.getConnection(), delete);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } finally {
            dbConnection.destroy();
        }

        if (errorString != null) {
            req.setAttribute("errorString", errorString);
            forwardToFragment("removeEmployee.jsp", req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/employee");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
