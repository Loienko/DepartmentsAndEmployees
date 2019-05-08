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
        System.out.println("errorDataDepartment - " + errorDataDepartment);
        req.setAttribute("errorDataDepartment", errorDataDepartment);
        forwardToFragment("add_new_department.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        boolean hasError = false;
        String errorDataDepartment = "";
        Department departmentAddNewDepart = null;

        String newNameDepartment = req.getParameter("newNameDepartment");

        if (newNameDepartment == null || newNameDepartment.isEmpty()) {
            hasError = true;
            errorDataDepartment = "Sorry, You have not entered the department name. Please enter a name";
        } else {
            DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
            Connection connection = new DBConnection().getConnection();
            departmentAddNewDepart = new Department(0, newNameDepartment, 0);
            try {
                if (!connection.isClosed()) {
                    String uniqueDepartmentName = dbUtilsDepartment.getUniqueDepartmentName(connection, newNameDepartment);
                    if (uniqueDepartmentName.isEmpty()) {
                        dbUtilsDepartment.addNewDepartment(connection, departmentAddNewDepart);
                    } else {
                        hasError = true;
                        errorDataDepartment = "Sorry, you input not unique department name. Please repeat your input ";
                    }
                } else {
                    hasError = true;
                    errorDataDepartment = "Sorry, problem with connection to DB, Try again later...";
                }
            } catch (SQLException e) {
//                throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
                errorDataDepartment = "Sorry, problem with connection DB, Try again later...";
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
