<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Employee section</title>
</head>
<body>

<p>
    <a href="${pageContext.servletContext.contextPath}/index.jsp">Main page</a>
</p>
<div style="border:1px solid #D0D0D0;width:1200px;padding:10px;">
<a href="${pageContext.servletContext.contextPath}/createEmployee">Add employee</a>
<table border="1">
    <tr>
        <td>id</td>
        <td>First Name</td>
        <td>Last name</td>
        <td>phone number</td>
        <td>email</td>
        <td>skype</td>
        <td>Entry date</td>
        <td>Experience</td>
        <td>Experience level</td>
        <td>Language level</td>
        <td>birthday</td>
        <td>Project</td>
        <td>Feedback</td>
    </tr>
    <c:forEach items="${employees}" var="employee">
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
        <td>${employee.birthDay}</td>
        <td>${employee.project.id}</td>
        <td>${employee.feedback.description}</td>
        <td><a href="${pageContext.servletContext.contextPath}/deleteEmployee?delete=${employee.id}">Delete</a></td>
        <td><a href="${pageContext.servletContext.contextPath}/editEmployee?edit=${employee.id}">Update</a></td>
    </tr>
    </c:forEach>
</table>
</div>
</body>
</html>