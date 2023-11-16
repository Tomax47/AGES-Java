<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>
<center>
    <h1>User Profile</h1>
    <c:if test="${user != null}">
        <form action="/profile" method="post" enctype="multipart/form-data">
            <!-- User,s profile pic display-->
            <c:if test="${not empty user.image}">
                <img src="data:image/jpeg;base64,${fn:escapeXml(user.image)}" alt="User Image" width="150" height="150"><br />
            </c:if>

            <!-- Data update input section -->
            <input class="btn btn-outline-dark" type="file" name="image"><br><br>
            Name: <input type="text" name="name" value="${user.name}"><br>
            Surname: <input type="text" name="surname" value="${user.surname}"><br>
            Age: <input type="text" name="age" value="${user.age}"><br>
            Number: <input type="text" name="number" value="${user.number}"><br>
            Address: <input type="text" name="address" value="${user.address}"><br>
            Email: <input type="text" name="email" readonly value="${user.email}"><br>
            <input type="submit" value="Update">
        </form>
    </c:if>

    <!-- FIX THE CODE BELOW SO IT SHOWS THE LINK FOR THE ADMINS -->
    <c:if test="${ user.getRole().equals(String.valueOf('Admin')) }">
        <a class="nav-link active" aria-current="page" href="/users_index" style="font-weight: bold; color: #641515; font-size: 1.5em; margin-top: 1em">Admin Panel</a>
    </c:if>
</center>
</body>
</html>
