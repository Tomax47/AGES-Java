<%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 11/5/2023
  Time: 4:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>
<center>

    <div class="cardContainer">
        <div class="card">
            <p class="login-header">Welcome Back!</p>

            <c:if test="${ loginErrorMessage != null }">
                <p style="color: #dc3545; font-weight: inherit;"><strong>${loginErrorMessage}</strong></p>
            </c:if>

            <form action="/login" method="post">
                <div class="mb-3 row">
                    <div class="email-label">
                        <label class="input-label">Email</label>
                    </div>
                    <div class="email-div">
                        <input type="text" class="input-div" name="email">
                    </div>
                </div>
                <div class="password-div">
                    <div class="psswd-label">
                        <label class="input-label">Password</label>
                    </div>
                    <div class="col-sm-10">
                        <input type="password" class="input-div" name="password">
                    </div>
                </div>
                <div class="login-btn">
                    <input type="submit" class="btn btn-outline-danger" value="Login">
                </div>
            </form>
            <p class="bottom-form-line">Don't have an account?<span><a class="bottom-form-link" href="register"> Register</a></span></p>
        </div>
    </div>
</center>
<%@include file="footer.jsp"%>
</body>
</html>
