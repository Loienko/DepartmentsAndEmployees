package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.connection.DBConnection;
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

@WebServlet("/addNewDepartment")
public class AddNewDepartmentController extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String errorDataDepartment = (String) session.getAttribute("errorDataDepartment");
        req.setAttribute("errorDataDepartment", errorDataDepartment);
        forwardToFragment("add_new_department.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        boolean hasError = false;
        String errorDataDepartment = "";
        HttpSession session = req.getSession();

        Department departmentAddNewDepart = null;
        String newNameDepartment = req.getParameter("newNameDepartment");

        if (newNameDepartment == null || newNameDepartment.isEmpty()) {
            hasError = true;
            errorDataDepartment = "Please Input correct name department !!!";
        } else {
            departmentAddNewDepart = new Department(0, newNameDepartment, 0);
            DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
            Connection connection = new DBConnection().getConnection();
            try {
                if (!connection.isClosed()) {
                    dbUtilsDepartment.addNewDepartment(connection, departmentAddNewDepart);
                } else {
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (SQLException e) {
//                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
                e.printStackTrace();
                forwardToPage("error.jsp", req, resp);
            }
        }

        if (hasError) {
            session.setAttribute("errorDataDepartment", errorDataDepartment);
            forwardToFragment("add_new_department.jsp", req, resp);
        } else {
            req.setAttribute("departmentAddNewDepart", departmentAddNewDepart);
            resp.sendRedirect(req.getContextPath() + "/department");
        }
    }
}
