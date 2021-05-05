<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Staff</title>
</head>
<body>

<p>
    <a href="${pageContext.servletContext.contextPath}/index.jsp">Главная страница</a>
</p>
<div style="border:1px solid #D0D0D0;width:600px;padding:10px;">

<table border="1">
    <tr>
        <td>id</td>
        <td>firstName</td>
        <td>lastName</td>
        <td>phoneNumber</td>
        <td>email</td>
        <td>skype</td>
        <td>entryDate</td>
        <td>experience</td>
        <td>experienceLevel</td>
        <td>languageLevel</td>
        <td>birthDay</td>
        <td>project</td>
        <td>feedback</td>

    </tr>
    <c:forEach items="${staff}" var="employee">
    <tr>
        <td>${employee.id}</td>
        <td>${employee.firstName}</td>
        <td>${employee.lastName}</td>
        <td>${employee.phoneNumber}</td>
        <td>${employee.email}</td>
        <td>${employee.skype}</td>
        <td>${employee.entryDate}</td>
        <td>${employee.experience}</td>
        <td>${employee.experienceLevel}</td>
        <td>${employee.languageLevel}</td>
        <td>${employee.project.name}</td>
        <td>${employee.feedback.description}</td>
    </tr>
    </c:forEach>

</table>
</div>
</body>
</html>