<%--<%@ page import="staffcontrol.constants.LanguageLevel" %>--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Save Employee</title>

    <link type="text/css"
          rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css">

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
    <h3>Fill in the form:</h3>

    <form action="${pageContext.servletContext.contextPath}/createEmployee" method="POST">
        <form:hidden path="id"/>

        <table>
            <tbody>
            <tr>
                <td align="right">First name :</td>
                <td>
                    <input type="text" name="firstName" required>
                </td>
            </tr>
            <tr>
                <td align="right">Last name :</td>
                <td>
                    <input type="text" name="lastName" required>
                </td>
            </tr>
            <tr>
                <td align="right">Phone number :</td>
                <td>
                    <input type="text" name="phoneNumber" required>
                </td>
            </tr>
            <tr>
                <td align="right">email :</td>
                <td>
                    <input type="email" name="email" required>
                </td>
            </tr>
            <tr>
                <td align="right">skype :</td>
                <td>
                    <input type="text" name="skype" required>
                </td>
            </tr>
            <tr>
                <td align="right">Entry Date :</td>
                <td>
                    <input type="date" name="entryDate" required>
                </td>
            </tr>
            <tr>
                <td align="right">Experience :</td>
                <td>
                    <input type="text" name="experience" required>
                </td>
            </tr>
            <tr>
                <td align="right">Experience Level :</td>
                <td>
                    <select name="experienceLevel">
                        <option value="${selected.name()}" selected>${selected}</option>
                        <c:forEach items="${experienceLevels}" var="experienceLevel">
                            <c:if test="${experienceLevel != selected}">
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
                        <option value="${selected.name()}" selected>${selected}</option>
                        <c:forEach items="${languageLevels}" var="languageLevel">
                            <c:if test="${languageLevel != selected}">
                                <option value="${languageLevel.name()}">${languageLevel}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">Birthday :</td>
                <td>
                    <input type="date" name="birthDay" required>
                </td>
            </tr>
            <tr>
                <td align="right">Project :</td>
                <td>
                    <select name="project">
                        <option value="${project.getId()}" selected>${project.getName()}</option>
                        <c:forEach items="${projects}" var="item">
                            <c:if test="${item != selected}">
                                <option value="${item.getId()}">${item.getName()}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <td align="right">Feedback :</td>
            <td>
                <input type="text" name="feedback" required>
            </td>
            </tr>

            <tr>
                <td><input type="submit" align="center" value="Создать"/></td>
            </tr>

            </tbody>
        </table>
        <p>
            <a href="${pageContext.servletContext.contextPath}/index.jsp">Main page</a>
        </p>
</body>
</html>
