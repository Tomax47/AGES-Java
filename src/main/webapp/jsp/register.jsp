<%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 11/5/2023
  Time: 4:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>

<br><br>
<h1>Create A New Account</h1>
<form action="/register" method="post">
    <div class="mb-3 row">
        <label for="staticEmail" class="col-sm-2 col-form-label">Name</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="staticName" name="name">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="staticEmail" class="col-sm-2 col-form-label">Surname</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="staticSurname" name="surname">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="staticEmail" name="email">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPassword" name="password">
        </div>
    </div>
    <div class="registration-btn">
        <input type="submit" class="btn btn-outline-danger" value="Register">
    </div>
</form>
</body>
</html>
