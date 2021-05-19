<%--<%@ page import="staffcontrol.constants.LanguageLevel" %>--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

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

    <sf:form method="POST" modelAttribute="employee">
    <table>
        <tr>
            <td align="right">First name :</td>
            <td>
                <sf:input path="firstName" type="text" name="firstName" value=""/>
            </td>
        </tr>
        <tr>
            <td align="right">Last name :</td>
            <td>
                <input type="text" name="lastName" value="">
            </td>
        </tr>
        <tr>
            <td align="right">Phone number :</td>
            <td>
                <sf:input path="phoneNumber" type="text" name="phoneNumber" value=""/>
            </td>
        </tr>
        <tr>
            <td align="right">email :</td>
            <td>
                <sf:input path="email" type="email" name="email" value=""/>
            </td>
        </tr>
        <tr>
            <td align="right">skype :</td>
            <td>
                <sf:input path="skype" type="text" name="skype" value=""/>
            </td>
        </tr>
        <tr>
            <td align="right">Entry Date :</td>
            <td>
                <sf:input path="entryDate" type="date" name="entryDate" value=""/>
            </td>
        </tr>
        <tr>
            <td align="right">Experience :</td>
            <td>
                <sf:input path="experience" type="text" name="experience" value=""/>
            </td>
        </tr>
        <tr>
            <td align="right">Experience Level :</td>
            <td>
                <sf:select path="experienceLevel" id="experienceLevels">
                    <c:forEach items="${experienceLevels}" var="experienceLevel">
                        <sf:option value="${experienceLevel}">
                            <c:out value="${experienceLevel.name()}"/>
                        </sf:option>
                    </c:forEach>
                </sf:select>
            </td>
        </tr>
        <tr>
            <td align="right">Language Level :</td>
            <td>
                <sf:select path="languageLevel" id="languageLevels">
                    <c:forEach items="${languageLevels}" var="languageLevel">
                        <sf:option value="${languageLevel}">
                            <c:out value="${languageLevel.name()}"/>
                        </sf:option>
                    </c:forEach>
                </sf:select>
            </td>
        </tr>
        <tr>
            <td align="right">Birthday :</td>
            <td>
                <sf:input path="birthDay" type="date" name="birthDay" value=""/>
            </td>
        </tr>

        <tr>
            <td align="right">Project :</td>
            <td>
                <sf:select path="project.id" id="projects">
                    <c:forEach items="${projects}" var="project">
                        <sf:option value="${project.id}">
                            <c:out value="${project.name}"/>
                        </sf:option>
                    </c:forEach>
                </sf:select>
            </td>
        </tr>

        <tr>
            <td align="right">Feedback :</td>
            <td>
                <sf:input path="feedback.description" type="text" name="feedbackDescription" value=""/>
            </td>
        </tr>

        <tr>
            <td><input type="submit" align="center" value="Save changes"/></td>
        </tr>

        </tbody>
    </table>
    </sf:form>
        <p>
            <a href="${pageContext.servletContext.contextPath}/index.jsp">Main page</a>
        </p>
</body>
</html>
