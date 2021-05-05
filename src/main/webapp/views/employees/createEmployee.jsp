<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add employee</title>

</head>
<body>
<p>
    <a href="${pageContext.servletContext.contextPath}/index.jsp">Main page</a>
</p>

<div style="border:1px solid #D0D0D0;width:600px;padding:10px;">
<form action="${pageContext.servletContext.contextPath}/createEmployee" method="POST">

    <table>
        <tr>
            <td align="right" >Name : </td>
            <td>
                <input type="text" name="firstName" required>
            </td>
        </tr>
        <tr>
            <td align="right" >Last name : </td>
            <td>
                <input type="text" name="lastName" required>
            </td>
        </tr>


    </table>
</form>
</div>
</body>
</html>