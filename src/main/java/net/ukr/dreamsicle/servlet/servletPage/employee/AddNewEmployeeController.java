package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.exception.ApplicationException;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;
import net.ukr.dreamsicle.util.DBUtilsEmployee;
import net.ukr.dreamsicle.validation.ValidEmailAddress;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@WebServlet("/addNewEmployee")
public class AddNewEmployeeController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(AddNewEmployeeController.class);
    private ValidEmailAddress validEmailAddress = new ValidEmailAddress();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String errorCreateNewEmployee = (String) session.getAttribute("errorCreateNewEmployee");
        req.setAttribute("errorCreateNewEmployee", errorCreateNewEmployee);

        String parameterNameDepartment = req.getParameter("nameDepartment");
        session.setAttribute("parameterNameDepartment", parameterNameDepartment);

        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();
        try {
            String uniqueDepartmentName = dbUtilsDepartment.uniqueParameter(parameterNameDepartment);
            if (!uniqueDepartmentName.isEmpty()) {
                forwardToFragment("add_new_employee.jsp", req, resp);
            } else {
                LOGGER.info("Modification the request");
                forwardToPage("error.jsp", req, resp);
            }
        } catch (SQLException | ApplicationException e) {
            LOGGER.error(e);
            forwardToPage("error.jsp", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        boolean hasError = false;
        String errorDataDepartment = "";
        Employee employeeAddNewEmployee = null;
        String parameterNameDepartment = (String) req.getSession().getAttribute("parameterNameDepartment");

        String nameEmployee = req.getParameter("nameEmployee");
        String surnameEmployee = req.getParameter("surnameEmployee");
        String emailEmployee = req.getParameter("emailEmployee");
        String dateEmployee = req.getParameter("dateEmployee");

        if (dateEmployee.isEmpty()) {
            dateEmployee = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        }
        boolean validUniqueEmailAddress = validEmailAddress.isValidUniqueEmailAddress(emailEmployee);

        if (nameEmployee.isEmpty() || surnameEmployee.isEmpty() || emailEmployee.isEmpty() || parameterNameDepartment.isEmpty()) {
            hasError = true;
            errorDataDepartment = "Please Input data (name, surname, email, date)!";
        } else {
            DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
            employeeAddNewEmployee = new Employee(nameEmployee, surnameEmployee, emailEmployee, getDateFormat(dateEmployee));
            try {
                boolean validEmailByDB = dbUtilsEmployee.isValidEmailByDB(emailEmployee);
                if (validUniqueEmailAddress && !validEmailByDB) {
                    dbUtilsEmployee.addNewEmployee(employeeAddNewEmployee, parameterNameDepartment);
                } else {
                    hasError = true;
                    errorDataDepartment = "Please input unique email address.";
                }
            } catch (SQLException | ApplicationException e) {
                LOGGER.error("error", e);
                forwardToPage("error.jsp", req, resp);
            }
        }

        if (hasError) {
            session.setAttribute("errorCreateNewEmployee", errorDataDepartment);
            forwardToFragment("add_new_employee.jsp", req, resp);
        } else {
            req.setAttribute("employeeAddNewEmployee", employeeAddNewEmployee);
            resp.sendRedirect(req.getContextPath() + "/employee");
        }
    }


    protected String getDateFormat(String dateEmployee) {
        String[] split = dateEmployee.split("-");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            stringBuilder.append(split[i]).append(".");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}
