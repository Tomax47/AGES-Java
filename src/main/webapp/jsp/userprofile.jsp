<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 11/8/2023
  Time: 8:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>

<h1>User Profile</h1>
<c:if test="${user != null}">
    <form action="/profile" method="post">
        Name: <input type="text" name="name" value="${user.name}"><br>
        Surname: <input type="text" name="surname" value="${user.surname}"><br>
        Age: <input type="text" name="age" value="${user.age}"><br>
        Number: <input type="text" name="number" value="${user.number}"><br>
        Address: <input type="text" name="address" value="${user.address}"><br>
        Email: <input type="text" name="email" readonly value="${user.email}"><br>
        <input type="submit" value="Update">
    </form>
</c:if>
</body>
</html>
