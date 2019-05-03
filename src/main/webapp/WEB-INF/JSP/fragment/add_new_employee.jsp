<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create new Employee</title>
</head>
<h2>Create new Employee</h2>

<form method="post" action="${pageContext.request.contextPath}/addNewEmployee">
    <table border="2">
        <tr>
            <td align="center">Name</td>
            <td align="center">
                <input type="text" name="nameEmployee" value="${employee.name}">
            </td>
        </tr>
        <tr>
            <td align="center">Surname</td>
            <td>
                <input type="text" name="surnameEmployee" value="${employee.surname}">
            </td>
        </tr>
        <tr>
            <td align="center">Email</td>
            <td>
                <input type="text" name="emailEmployee" value="${employee.email}">
            </td>
        </tr>
        <tr>
            <td align=" center">Date
            </td>
            <td>
                <input type="date" name="dateEmployee" value=<%= new Date()%>>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <a href="/employee">Cancel</a>
                <input type="submit" value="Add">
            </td>
        </tr>
    </table>
</form>
</html>
