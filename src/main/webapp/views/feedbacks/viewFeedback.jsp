<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>List of feedbacks</title>
</head>

<body>
<p>
    <a href="${pageContext.servletContext.contextPath}/index.jsp">Main page</a>
</p>

<table border="1">
    <tr>
        <td>id</td>
        <td>Description</td>
        <td>Created</td>
    </tr>
    <c:forEach items="${feedbacks}" var="feedback">
    <tr>
        <td>${feedback.id}</td>
        <td>${feedback.description}</td>
        <td>${feedback.created}</td>
    </tr>
    </c:forEach>
</body>
</html>