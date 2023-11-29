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
    <title>Register</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>

<center>
    <div class="cardContainer">
        <div class="card">
            <p class="login-header">Create New Account</p>

            <c:if test="${ registerErrorMessage != null }">
                <p style="color: #dc3545; font-weight: inherit;"><strong>${ registerErrorMessage }</strong></p>
            </c:if>

            <form action="/register" method="post">
                <div class="mb-3 row">
                    <label class="input-label">Name</label>
                    <div class="name-div">
                        <input type="text" class="input-div" name="name">
                    </div>
                </div>
                <div class="mb-3 row">
                    <label class="input-label">Surname</label>
                    <div class="surname-div">
                        <input type="text" class="input-div" name="surname">
                    </div>
                </div>
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
                    <input type="submit" class="btn btn-outline-danger" value="Register">
                </div>
            </form>
            <p class="bottom-form-line">Already have an account?<span><a class="bottom-form-link" href="login"> Login</a></span></p>
        </div>
    </div>

</center>
<%@include file="footer.jsp"%>
</body>
</html>
