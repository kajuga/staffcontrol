<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Feedbacks</title>
    <link type="text/css"
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css" />
</head>

<body>

<div id="wrapper">
    <div id="header">
        <h2>Staffcontrol Managment</h2>
    </div>
</div>
<div id="container">
    <div id="content">
        <table>
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
        </table>
    </div>
</div>
<a href="${pageContext.servletContext.contextPath}/index.jsp">Main page</a>
</body>
</html>