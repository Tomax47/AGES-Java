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
    <title>Login</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>

<br><br>
<h1>Welcome Back!</h1>
<form action="/login" method="post">
    <div class="mb-3 row">
        <label class="col-sm-2 col-form-label">Email</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="email">
        </div>
    </div>
    <div class="mb-3 row">
        <label class="col-sm-2 col-form-label">Password</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" name="password">
        </div>
    </div>
    <div class="login-btn">
        <input type="submit" class="btn btn-outline-danger" value="Login">
    </div>
</form>

</body>
</html>
