<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employees</title>
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
        <input type="button" value="Add Employee"
               onclick="window.location.href='createEmployee'; return false;"
               class="add-button"
        />
        <table>
    <tr>
<%--        <th>id</th>--%>
        <th>First Name</th>
        <th>Last name</th>
        <th>phone number</th>
        <th>email</th>
        <th>skype</th>
        <th>Entry date</th>
        <th>Experience</th>
        <th>Experience level</th>
        <th>Language level</th>
        <th>birthday</th>
        <th>Project</th>
        <th>Feedback</th>
        <th>Action</th>
    </tr>

    <c:forEach var="employee" items="${employees}">

        <c:url var="updateLink" value="/editEmployee">
            <c:param name="employeeId" value="${employee.id}" />
        </c:url>

        <c:url var="deleteLink" value="/deleteEmployee">
            <c:param name="employeeId" value="${employee.id}" />
        </c:url>

        <tr>
<%--        <td>${employee.id}</td>--%>
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
        <td>${employee.project.name}</td>
        <td>${employee.feedback.description}</td>
            <td>
                <a href="${updateLink}">Update</a>|<a href="${deleteLink}"
                                                      onclick="if (!(confirm('Are you sure you want to delete this employee?'))) return false">Delete</a>
            </td>
    </tr>
    </c:forEach>
        </table>
    </div>
</div>
    <a href="${pageContext.servletContext.contextPath}/index.jsp">Main page</a>
</body>
</html>