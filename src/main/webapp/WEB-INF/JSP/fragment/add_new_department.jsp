<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create new Department</title>
</head>
<h2>Create Department</h2>

<form method="post" action="${pageContext.request.contextPath}/addNewDepartment">
    <table border="0">
        <tr>
            <td>Specify the name of the department:</td>
            <td>
                <input type="text" name="newNameDepartment" value="${department.name_depart}">
            </td>
        </tr>
        <tr>
            <td align="right" colspan="2">
                <a href="department">Cancel</a>
                <input type="submit" value="Add">
            </td>
        </tr>
    </table>
</form>
</html>



