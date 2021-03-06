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
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editDepartment")
public class EditDepartmentController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(EditDepartmentController.class);
    private String nameDepartParameter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String errorEditDepartment = (String) session.getAttribute("errorEditDepartment");
        req.setAttribute("errorEditDepartment", errorEditDepartment);
        nameDepartParameter = req.getParameter("nameDepart");
        req.setAttribute("nameDepartParameter", nameDepartParameter);
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
        String uniqueDepartmentName = null;
        try {
            uniqueDepartmentName = dbUtilsDepartment.uniqueParameter(nameDepartParameter);
        } catch (SQLException | ApplicationException e) {
            LOGGER.error(e);
            forwardToPage("error.jsp", req, resp);
        }
        if (nameDepartParameter.isEmpty() || uniqueDepartmentName.isEmpty()) {
            forwardToPage("error.jsp", req, resp);
        } else {

            try {
                Department departmentForUpdate = (Department) dbUtilsDepartment.findParameterForUpdate(nameDepartParameter);
                req.setAttribute("departmentForUpdate", departmentForUpdate);
                forwardToFragment("editDepartment.jsp", req, resp);

            } catch (SQLException | ApplicationException e) {
                LOGGER.error(e);
                forwardToPage("error.jsp", req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        @Pattern(regexp = "[A-Za-zА-Яа-яЁё0-9-_]{1,50}")
        String nameUpdateDepartment = req.getParameter("nameUpdateDepartment");
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
        String uniqueDepartmentName = null;
        try {
            uniqueDepartmentName = dbUtilsDepartment.uniqueParameter(nameUpdateDepartment);
        } catch (SQLException | ApplicationException e) {
            LOGGER.error("error", e);
            forwardToPage("error.jsp", req, resp);
        }

        if (!nameUpdateDepartment.isEmpty() && uniqueDepartmentName.isEmpty()) {
            try {
                dbUtilsDepartment.updateDepartment(nameDepartParameter, nameUpdateDepartment);
                resp.sendRedirect(req.getContextPath() + "/department");
            } catch (ApplicationException | SQLException e) {
                LOGGER.error("error", e);
                forwardToPage("error.jsp", req, resp);
            }
        } else {
            LOGGER.info("nameUpdateDepartment not unique");
            resp.sendRedirect(req.getContextPath() + "/department");
        }
    }
}
