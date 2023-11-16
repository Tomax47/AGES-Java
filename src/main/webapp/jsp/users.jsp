<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.AGES.dto.UserDto" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>

<center>
    <c:if test="${ AdminUserBanMsg != null }">
        <p style="color: #641515; font-weight: bold; margin: 1.5em"><strong>${AdminUserBanMsg}</strong></p>
    </c:if>
</center>
<div class="container mt-4">
    <h1 class="text-center">Admin User Control Panel</h1>

    <div class="row">
        <% List<UserDto> users = (List<UserDto>) request.getAttribute("usersList");
            if (users != null && !users.isEmpty()) {
                for (UserDto user : users) { %>
        <center>
            <form action="/users_index" method="post">
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <img src="data:image/jpeg;base64,<%= user.getImage() %>" alt="User Image" width="150" height="150"><br />

                            <h2 class="card-title" style="color: #641515; font-weight: bold; margin-top: 0.5em">User's Info</h2>
                            <p class="card-text"><strong>Name: </strong><%= user.getName() %></p>
                            <p class="card-text"><strong>Surname: </strong><%= user.getSurname() %></p>
                            <p class="card-text"><strong>Age: </strong><%= user.getAge() %></p>
                            <p class="card-text"><strong>Address: </strong><%= user.getAddress() %></p>
                            <p class="card-text"><strong>Number: </strong><%= user.getNumber() %></p>
                            <p class="card-text"><strong>IsAdmin: </strong><%= user.isAdmin() %></p>
                            <input class="card-text" name="user_email" readonly value="<%= user.getEmail() %>">

                            <div class="submit-btn">
                                <input class="btn btn-outline-danger" type="submit" value="Ban User">
                            </div>
                        </div>
                    </div>
                </div>
            </form>

        </center>
        <% }
        } else { %>
        <div class="col-12 text-center">
            <p>No users available.</p>
        </div>
        <% } %>
    </div>
</div>


</body>
</html>
