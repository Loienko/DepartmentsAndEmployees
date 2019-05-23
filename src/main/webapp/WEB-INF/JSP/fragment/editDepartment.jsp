<%@ page import="static net.ukr.dreamsicle.consts.Constants.REGEX_CHECK_VALID_NAME_DEPARTMENT" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit department</title>
</head>
<body>
<h3>Edit Department</h3>
<p style="color: red">${errorEditDepartment}</p>
<form method="post" action="editDepartment">
    <tr>
        <td>Name Department</td>
        <td><input type="text"
                   pattern=<%=REGEX_CHECK_VALID_NAME_DEPARTMENT%> name="nameUpdateDepartment"
                   placeholder="${departmentForUpdate.name_depart}"
                   value="${departmentForUpdate.name_depart}"
                   autocomplete="on | off"
        /></td>
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
