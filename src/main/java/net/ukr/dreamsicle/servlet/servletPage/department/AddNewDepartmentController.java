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

@WebServlet("/addNewDepartment")
public class AddNewDepartmentController extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToFragment("add_new_department.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
        Connection connection = new DBConnection().getConnection();

        String newNameDepartment = req.getParameter("newNameDepartment");

        Department departmentAddNewDepart = new Department(0, newNameDepartment, 0);

        try {
            dbUtilsDepartment.addNewDepartment(connection, departmentAddNewDepart);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("departmentAddNewDepart", departmentAddNewDepart);

//        forwardToPage("department.jsp", req, resp);
        resp.sendRedirect(req.getContextPath() + "/department");
    }
}
