package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/editDepartment")
public class EditDepartment extends AbstractServlet {
    String nameDepartParameter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        nameDepartParameter = req.getParameter("nameDepart");
        req.setAttribute("nameDepartParameter", nameDepartParameter);
        Connection connection = new DBConnection().getConnection();
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
        Department departmentForUpdate = null;

        try {
            departmentForUpdate = dbUtilsDepartment.findDepartmentForUpdate(connection, nameDepartParameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("departmentForUpdate", departmentForUpdate);
        forwardToFragment("editDepartment.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameUpdateDepartment = req.getParameter("nameUpdateDepartment");

        Connection connection = new DBConnection().getConnection();
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();

        try {
            dbUtilsDepartment.updateDepartment(connection, nameDepartParameter, nameUpdateDepartment);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/department");
    }
}
