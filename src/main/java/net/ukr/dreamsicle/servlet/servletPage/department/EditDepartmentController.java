package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.exception.ApplicationException;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;

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
//                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
                e.printStackTrace();
                forwardToPage("error.jsp", req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String nameUpdateDepartment = req.getParameter("nameUpdateDepartment");
        String errorEditDepartment = "";
        HttpSession session = req.getSession();

        Connection connection = new DBConnection().getConnection();
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();

        String uniqueDepartmentName = null;
        try {
            uniqueDepartmentName = dbUtilsDepartment.getUniqueDepartmentName(connection, nameUpdateDepartment);
            System.out.println(uniqueDepartmentName);
        } catch (SQLException e) {
//            e.printStackTrace();
            forwardToPage("error.jsp", req, resp);
        }

        if (!nameUpdateDepartment.isEmpty() && uniqueDepartmentName.isEmpty()) {
            try {
                if (!connection.isClosed()) {
                    dbUtilsDepartment.updateDepartment(connection, nameDepartParameter, nameUpdateDepartment);
                    resp.sendRedirect(req.getContextPath() + "/department");
                } else {
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
//                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
                forwardToPage("error.jsp", req, resp);
            }
        } else {
//            errorEditDepartment = "You have not entered the department name, please repeat your input.";
//            session.setAttribute("errorEditDepartment", errorEditDepartment);
//            forwardToFragment("editDepartment.jsp", req, resp);
            resp.sendRedirect(req.getContextPath() + "/department");
        }
    }
}
