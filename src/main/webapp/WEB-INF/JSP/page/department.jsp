<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static net.ukr.dreamsicle.consts.Constants.ADD" %>
<%@ page import="static net.ukr.dreamsicle.consts.Constants.EDIT" %>
<%@ page import="static net.ukr.dreamsicle.consts.Constants.REMOVE" %>
<%@ page import="static net.ukr.dreamsicle.consts.Constants.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
    <title>List Department</title>

</head>
<body>
<table border="3">
    <tbody>
    <tr>
        <td> Names department</td>
        <td> Count Employee</td>
        <td colspan="3" align="center">Action</td>
    </tr>
    <c:forEach items="${depart}" var="departments">
        <tr>
            <td align="center">${departments.nameDepart}</td>
            <td align="center">${departments.countEmployee}</td>
            <td>
                <a href="editDepartment?nameDepart=${departments.nameDepart}"/> <%= EDIT%>
            </td>
            <td><a href="removeDepartment?nameDepart=${departments.nameDepart}"/> <%= REMOVE%>
            </td>
            <td><a href="employee?nameDepart=${departments.nameDepart}"/><%= EMPLOYEES_LIST%>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="/addNewDepartment"><%= ADD%>
</a>
</body>
</html>
