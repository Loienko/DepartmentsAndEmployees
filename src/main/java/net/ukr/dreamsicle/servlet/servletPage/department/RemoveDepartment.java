package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/removeDepartment")
public class RemoveDepartment extends AbstractServlet {

    public RemoveDepartment() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("nameDepart");

        DBConnection dbConnection = new DBConnection();
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();

        String errorString = null;
        try {
            dbUtilsDepartment.deleteDepartment(dbConnection.getConnection(), delete);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } finally {
            dbConnection.destroy();
        }

        if (errorString != null) {
            req.setAttribute("errorString", errorString);
            forwardToFragment("removeDepartment.jsp", req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/department");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
