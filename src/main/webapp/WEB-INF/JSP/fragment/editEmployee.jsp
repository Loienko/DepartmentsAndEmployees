<%@ page import="static net.ukr.dreamsicle.consts.Constants.REGEX_CHECK_VALID_NAME_SURNAME_EMPLOYEE" %>
<%@ page import="static net.ukr.dreamsicle.consts.Constants.REGEX_CHECK_VALID_EMAIL_ADDRESS" %>
<%@ page import="static net.ukr.dreamsicle.consts.Constants.ADD" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit employee</title>
</head>
<body>
<h3>Edit Employee</h3>
<p style="color: red">${errorEditEmployee}</p>
<form method="post" action="editEmployee">
    <table border="2">
        <tr>
            <td align="center">Name</td>
            <td style="color: #1c2eff;">${employeeForUpdate.name}</td>
            <td align="center">
                <input type="text"
                       placeholder="${employeeForUpdate.name}"
                       pattern=<%=REGEX_CHECK_VALID_NAME_SURNAME_EMPLOYEE%> name="updateNameEmployee"
                       value="${employeeForUpdate.name}
">
            </td>
        </tr>
        <tr>
            <td align="center">Surname</td>
            <td style="color: #1c2eff;">${employeeForUpdate.surname}</td>
            <td>
                <input type="text"
                       placeholder="${employeeForUpdate.surname}"
                       pattern=<%=REGEX_CHECK_VALID_NAME_SURNAME_EMPLOYEE%> name="updateSurnameEmployee"
                       value="${employeeForUpdate.surname}">
            </td>

        </tr>
        <tr>
            <td align="center">Email</td>
            <td style="color: #1c2eff;">${employeeForUpdate.email}</td>
            <td>
                <input type="email"
                       placeholder="${employeeForUpdate.email}"
                       pattern=<%=REGEX_CHECK_VALID_EMAIL_ADDRESS%> name="updateEmailEmployee"
                       value="${employeeForUpdate.email}">
            </td>
        </tr>
        <tr>
            <td align="center">Date</td>
            <td style="color: #1c2eff;">${employeeForUpdate.createDate}</td>
            <td>
                <input type="date"
                       placeholder="${employeeForUpdate.createDate}"
                       name="updateDateEmployee"
                <%--value="${employeeForUpdate.createDate}">--%>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="3">
                <a href="/employee">Cancel</a>
                <input type="submit" value=<%=ADD%>>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
