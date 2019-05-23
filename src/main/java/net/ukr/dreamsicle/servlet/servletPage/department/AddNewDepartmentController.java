package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.exception.ApplicationException;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addNewDepartment")
public class AddNewDepartmentController extends AbstractServlet {


    private static final Logger LOGGER = Logger.getLogger(AddNewDepartmentController.class);

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
        HttpSession session = req.getSession();
        boolean hasError = false;
        String errorDataDepartment = "";
        Department departmentAddNewDepart = null;

        String newNameDepartment = req.getParameter("newNameDepartment");

        if (newNameDepartment == null || newNameDepartment.isEmpty()) {
            hasError = true;
            LOGGER.info("newNameDepartment not input");
            errorDataDepartment = "Sorry, You have not entered the department name. Please enter a name.";
        } else {
            DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
            departmentAddNewDepart = new Department(0, newNameDepartment, 0);
            try {
                String uniqueDepartmentName = dbUtilsDepartment.getUniqueDepartmentName(newNameDepartment);
                if (uniqueDepartmentName.isEmpty()) {
                    dbUtilsDepartment.addNewDepartment(departmentAddNewDepart);
                } else {
                    hasError = true;
                    LOGGER.info("Not unique department name");
                    errorDataDepartment = "Sorry, you input not unique department name. Please repeat your input. ";
                }
            } catch (ApplicationException | SQLException e) {
                LOGGER.error("error", e);
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
