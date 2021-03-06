<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<html>
<head>
    <h3> Department: ${sessionScope.nameDepartFromDepartment}</h3>
</head>
<body>
<table border="2">
    <tbody>
    <tr>
        <td align="center">Name</td>
        <td align="center">Surname</td>
        <td align="center">Email</td>
        <td align="center">Date</td>
        <td colspan="2" align="center">Action</td>
    </tr>
    <c:forEach items="${employeeList}" var="employee">
        <tr>
            <td align="center"> ${employee.name} </td>
            <td align="center"> ${employee.surname} </td>
            <td> ${employee.email} </td>
            <td align="center"> ${employee.createDate}</td>
            <td><a href="editEmployee?emailEmployee=${employee.email}"/>Edit</td>
            <td><a href="removeEmployee?emailEmployee=${employee.email}"/>Remove</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p>
    <a href="${pageContext.request.contextPath}/addNewEmployee?nameDepartment=${sessionScope.nameDepartFromDepartment}">
        Add new employee </a></p>
<a href="/department"> Back </a>
</body>
</html>
