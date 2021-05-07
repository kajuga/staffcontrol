
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main page</title>
    <link type="text/css"
          rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body bgcolor="#d4d7dc">
<div id="wrapper">
    <div id="header">
        <h2>Staffcontrol Managment</h2>
    </div>
</div>

<p></p>
<p></p>
<p><a href="employees">Employees</a></p>
<p><a href="feedbacks">View feedbacks</a></p>

<hr>

<p><a href="ServletAddFeedback">Добавить тест Feedback</a></p>
<p><a href="ServletAddProject">Добавить test Project</a></p>
<p><a href="ServletAddTeam">Добавить test Team</a></p>
<p><a href="ServletAddEmployee">Добавить test Employee</a></p>



</body>
</html>
