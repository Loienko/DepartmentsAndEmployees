<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit employee</title>
</head>
<body>
<h3>Edit Employee</h3>

<form method="post" action="editEmployee">
    <table border="2">
        <tr>
            <td align="center">Name</td>
            <td align="center">
                <input type="text" name="updateNameEmployee" value="${employeeForUpdate.name}">
            </td>
        </tr>
        <tr>
            <td align="center">Surname</td>
            <td>
                <input type="text" name="updateSurnameEmployee" value="${employeeForUpdate.surname}">
            </td>
        </tr>
        <tr>
            <td align="center">Email</td>
            <td>
                <input type="text" name="updateEmailEmployee" value="${employeeForUpdate.email}">
            </td>
        </tr>
        <tr>
            <td align="center">Date</td>
            <td>
                <input type="date" name="updateDateEmployee" value="${employeeForUpdate.createDate}">
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
</body>
</html>
