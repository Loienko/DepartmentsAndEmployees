package net.ukr.dreamsicle.servlet.servletPage.employee;

import net.ukr.dreamsicle.beans.Employee;
import net.ukr.dreamsicle.connection.MyUtils;
import net.ukr.dreamsicle.exception.ApplicationException;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsEmployee;
import net.ukr.dreamsicle.validation.ValidEmailAddress;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/editEmployee")
public class EditEmployeeController extends AbstractServlet {
    private static final Logger LOGGER = Logger.getLogger(EditEmployeeController.class);
    private String emailEmployee;
    private ValidEmailAddress validEmailAddress = new ValidEmailAddress();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Connection conn = MyUtils.getStoredConnection(req);
        String errorEditEmployee = (String) session.getAttribute("errorEditEmployee");
        req.setAttribute("errorEditEmployee", errorEditEmployee);

        emailEmployee = req.getParameter("emailEmployee");

        if (emailEmployee.isEmpty()) {
            LOGGER.info("emailEmployee is empty");
            forwardToPage("error.jsp", req, resp);
        } else {
            DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();
            try {
                Employee employeeForUpdate = dbUtilsEmployee.findEmployeeForUpdate(conn, emailEmployee);
                req.setAttribute("employeeForUpdate", employeeForUpdate);
                session.setAttribute("employeeForUpdate", employeeForUpdate);
                forwardToFragment("editEmployee.jsp", req, resp);

            } catch (SQLException | ApplicationException e) {
                LOGGER.error(e);
                forwardToPage("error.jsp", req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String errorEditEmployee = "";
        boolean error = false;
        Connection conn = MyUtils.getStoredConnection(req);
        String updateNameEmployee = req.getParameter("updateNameEmployee");
        String updateSurnameEmployee = req.getParameter("updateSurnameEmployee");
        String updateEmailEmployee = req.getParameter("updateEmailEmployee");
        String updateDateEmployee = req.getParameter("updateDateEmployee");

        boolean validUniqueEmailAddress = validEmailAddress.isValidUniqueEmailAddress(updateEmailEmployee);

//        Connection connection = new DBConnection().getConnection();
        DBUtilsEmployee dbUtilsEmployee = new DBUtilsEmployee();

        boolean validEmailByDB = false;
        try {
            validEmailByDB = dbUtilsEmployee.isValidEmailByDB(conn, updateEmailEmployee);
        } catch (SQLException e) {
            LOGGER.error(e);
            forwardToPage("error.jsp", req, resp);
        }


        if (!updateNameEmployee.isEmpty() && !updateSurnameEmployee.isEmpty() && !updateEmailEmployee.isEmpty()) {
            Employee employeeUpdate = new Employee(updateNameEmployee, updateSurnameEmployee, updateEmailEmployee, getDateFormat(updateDateEmployee));
            StringBuffer createQueryForBD = new StringBuffer();
            List arrayListValueField = new ArrayList();
            Employee employeeForUpdateFromDB = (Employee) session.getAttribute("employeeForUpdate");


            getCorrectName(employeeUpdate, createQueryForBD, arrayListValueField, employeeForUpdateFromDB);
            getCorrectSurname(employeeUpdate, createQueryForBD, arrayListValueField, employeeForUpdateFromDB);
            if (!employeeForUpdateFromDB.getEmail().equals(employeeUpdate.getEmail())) {
                if (validUniqueEmailAddress && !validEmailByDB) {
                    createQueryForBD.append(" email = ?,");
                    arrayListValueField.add(employeeUpdate.getEmail());
                } else {
                    error = true;
                    LOGGER.info("email not unique");
                    errorEditEmployee = "Sorry, that email not unique, Please, input unique email address.";
                }
            }

            if (!employeeForUpdateFromDB.getCreateDate().equals(employeeUpdate.getCreateDate())) {
                if (employeeUpdate.getCreateDate().isEmpty()) {
                    createQueryForBD.append(" date = ?,");
                    arrayListValueField.add(employeeForUpdateFromDB.getCreateDate());
                } else {
                    createQueryForBD.append(" date = ?,");
                    arrayListValueField.add(employeeUpdate.getCreateDate());
                }
            }


            String fieldForUpdate = "";
            if (createQueryForBD.length() != 0) {
                fieldForUpdate = createQueryForBD.substring(0, createQueryForBD.length() - 1);
            }

            try {
                if (!fieldForUpdate.isEmpty()) {
                    if (!error) {
                        dbUtilsEmployee.updateEmployee(conn, fieldForUpdate, arrayListValueField, emailEmployee);
                    } else {
                        req.setAttribute("emailEmployee", emailEmployee);
                        session.setAttribute("errorEditEmployee", errorEditEmployee);
                        forwardToFragment("editEmployee.jsp", req, resp);
                    }
                } else {
                    LOGGER.info("Connection with DB closed");
                    forwardToPage("error.jsp", req, resp);
                }
            } catch (SQLException | ApplicationException e) {
                LOGGER.error("error", e);
                forwardToPage("error.jsp", req, resp);
            }
        } else {
            LOGGER.info("not input all fields");
            error = true;
            errorEditEmployee = "Please, input all fields.";
        }

        if (error) {
            req.setAttribute("emailEmployee", emailEmployee);
            session.setAttribute("errorEditEmployee", errorEditEmployee);
            forwardToFragment("editEmployee.jsp", req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/employee");
        }
    }

    protected void getCorrectSurname(Employee employeeUpdate, StringBuffer createQueryForBD, List arrayListValueField, Employee employeeForUpdateFromDB) {
        if (!employeeForUpdateFromDB.getSurname().equals(employeeUpdate.getSurname())) {
            createQueryForBD.append(" surname = ?,");
            arrayListValueField.add(employeeUpdate.getSurname());
        }
    }

    protected void getCorrectName(Employee employeeUpdate, StringBuffer createQueryForBD, List arrayListValueField, Employee employeeForUpdateFromDB) {
        if (!employeeForUpdateFromDB.getName().equals(employeeUpdate.getName())) {
            createQueryForBD.append(" name = ?,");
            arrayListValueField.add(employeeUpdate.getName());
        }
    }

    protected String getDateFormat(String dateEmployee) {
        String[] split = dateEmployee.split("-");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = split.length - 1; i >= 0; i--) {
            stringBuffer.append(split[i]).append(".");
        }
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }
}
