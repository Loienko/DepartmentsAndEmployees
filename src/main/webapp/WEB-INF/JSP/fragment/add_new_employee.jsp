<%@ page import="static net.ukr.dreamsicle.consts.Constants.REGEX_CHECK_VALID_EMAIL_ADDRESS" %>
<%@ page import="static net.ukr.dreamsicle.consts.Constants.REGEX_CHECK_VALID_NAME_SURNAME_EMPLOYEE" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create new Employee</title>
</head>
<h2>Create new Employee</h2>
<p style="color: red">${errorAddDataEmployee}</p>
<form method="post" action="${pageContext.request.contextPath}/addNewEmployee">
    <table border="2">
        <tr>
            <td align="center">Name</td>
            <td align="center">
                <input type="text" placeholder="Name"
                       pattern=<%=REGEX_CHECK_VALID_NAME_SURNAME_EMPLOYEE%> name="nameEmployee"
                       value="${employee.name}">
            </td>
        </tr>
        <tr>
            <td align="center">Surname</td>
            <td>
                <input type="text" placeholder="Surname"
                       pattern=<%=REGEX_CHECK_VALID_NAME_SURNAME_EMPLOYEE%> name="surnameEmployee"
                       value="${employee.surname}">
            </td>
        </tr>
        <tr>
            <td align="center">Email</td>
            <td>
                <input type="email" pattern=<%=REGEX_CHECK_VALID_EMAIL_ADDRESS%>
                        name="emailEmployee" value="${employee.email} " placeholder="example: email@ukr.net">
            </td>
        </tr>
        <tr>
            <td align=" center">Date
            </td>
            <td>
                <input type="date" name="dateEmployee">
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
