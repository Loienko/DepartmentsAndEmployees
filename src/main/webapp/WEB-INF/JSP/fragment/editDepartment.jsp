<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit department</title>
</head>
<body>
<h3>Edit Department</h3>

<form method="post" action="editDepartment">
    <tr>
        <td>Name Department</td>
        <td><input type="text" name="nameUpdateDepartment" value="${departmentForUpdate.name_depart}"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Submit"/>
            <a href="/department">Cancel</a>
        </td>
    </tr>
</form>
</body>
</html>
