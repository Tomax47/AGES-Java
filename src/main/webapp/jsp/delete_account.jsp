<%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 11/28/2023
  Time: 7:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Account</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>
<center>
    <h1 class="text-center" style="color: #dc3545">Delete Profile</h1>
    <p class="warning-text-explanation" style="color: azure">By clicking on the delete my profile button, you consent to deleting your entire data from the site, including any products shared & any activities you have carried out on AGES!</p>
    <p class="warning-txt" style="color: #dc3545">ARE YOU SURE YOU WANT TO DELETE YOUR ACCOUNT?</p>

    <form action="/delete_account" method="post">
        <input class="btn btn-outline-danger" type="submit" value="DELETE MY PROFILE">
    </form>
</center>
<%@include file="footer.jsp"%>
</body>
</html>
