package net.ukr.dreamsicle.servlet.servletPage.department;

import net.ukr.dreamsicle.beans.Department;
import net.ukr.dreamsicle.connection.DBConnection;
import net.ukr.dreamsicle.servlet.AbstractServlet;
import net.ukr.dreamsicle.util.DBUtilsDepartment;
import org.omg.CORBA.portable.ApplicationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/department")
public class DepartmentController extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> departmentList = null;

        DBConnection dbConnection = new DBConnection();
        DBUtilsDepartment dbUtilsDepartment = new DBUtilsDepartment();


            departmentList = dbUtilsDepartment.getListDepartment(dbConnection.getConnection());

        req.setAttribute("depart", departmentList);
        forwardToPage("department.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
