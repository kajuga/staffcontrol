<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Update employee</title>
    <link type="text/css"
          rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css" />
    <link type="text/css"
          rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/add-employee.css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h2>Staffcontrol Managment</h2>
    </div>
</div>
<div id="container">
    <h3>Make the necessary changes :</h3>

    <form action="${pageContext.servletContext.contextPath}/editEmployee?id=${employee.id}" method="POST">
        <form:hidden path="id"/>

        <table>
            <tbody>
            <tr>
                <td align="right" hidden>id : </td>
                <td align="right" hidden>${employee.id} </td>
            </tr>

            <tr>
                <td align="right">First name :</td>
                <td>
                    <input type="text" name="firstName" value="${employee.firstName}">
                </td>
            </tr>
            <tr>
                <td align="right">Last name :</td>
                <td>
                    <input type="text" name="lastName" value="${employee.lastName}">
                </td>
            </tr>
            <tr>
                <td align="right">Phone number :</td>
                <td>
                    <input type="text" name="phoneNumber" value="${employee.phoneNumber}">
                </td>
            </tr>
            <tr>
                <td align="right">email :</td>
                <td>
                    <input type="email" name="email" value="${employee.email}">
                </td>
            </tr>
            <tr>
                <td align="right">skype :</td>
                <td>
                    <input type="text" name="skype" value="${employee.skype}">
                </td>
            </tr>
            <tr>
                <td align="right">Entry Date :</td>
                <td>
                    <input type="date" name="entryDate" value="${employee.entryDate}">
                </td>
            </tr>
            <tr>
                <td align="right">Experience :</td>
                <td>
                    <input type="text" name="experience" value="${employee.experience}">
                </td>
            </tr>
            <tr>
                <td align="right">Experience Level :</td>
                <td>
                    <select name="experienceLevel">
                        <option value="${employee.experienceLevel.name()}" selected>${employee.experienceLevel}</option>
                        <c:forEach items="${experienceLevels}" var="experienceLevel">
                            <c:if test="${experienceLevel != employee.experienceLevel}">
                                <option value="${experienceLevel.name()}">${experienceLevel}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">Language Level :</td>
                <td>
                    <select name="languageLevel">
                        <option value="${employee.languageLevel.name()}" selected>${employee.languageLevel}</option>
                        <c:forEach items="${languageLevels}" var="languageLevel">
                            <c:if test="${languageLevel != employee.languageLevel}">
                                <option value="${languageLevel.name()}">${languageLevel}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">Birthday :</td>
                <td>
                    <input type="date" name="birthDay" value="${employee.birthDay}">
                </td>
            </tr>

            <tr>
                <td align="right">Project :</td>
                <td>
                    <select name="project">
                        <option value="${employee.project.id}" selected>${employee.project.name}</option>
                        <c:forEach items="${projects}" var="project">
                            <c:if test="${project.id != employee.project.id}">
                                <option value="${project.id}">${project.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>

<%--            HARDCODE variant: just three position for select, just note for info--%>
<%--            <tr>--%>
<%--                <td align="right">Project :</td>--%>
<%--                <td>--%>
<%--                    <select name="project">--%>
<%--                        <option value="Project from HARDCODED sheet #1">Project #1</option>--%>
<%--                        <option selected value="Project from HARDCODED sheet DEFAULT">Default hardcoded project</option>--%>
<%--                        <option value="Project from HARDCODED sheet #1">Project #1</option>--%>
<%--                    </select>--%>
<%--                </td>--%>
<%--            </tr>--%>


            <td align="right">Feedback :</td>
            <td>
                <input type="text" name="feedback" value="${employee.feedback.description}">
            </td>
            </tr>

            <tr>
                <td><input type="submit" align="center" value="Save changes"/></td>
            </tr>

            </tbody>
        </table>
        <p>
            <a href="${pageContext.servletContext.contextPath}/index.jsp">back to Main page</a>
        </p>
</body>
</html>