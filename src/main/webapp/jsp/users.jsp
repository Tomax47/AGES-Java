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

<div class="container mt-4">
    <center>
        <h1 class="text-center">Admin User Control Panel</h1>
        <c:if test="${ AdminUserBanMsg != null }">
            <p style="color: #dc3545; font-weight: bold; margin: 1.5em"><strong>${AdminUserBanMsg}</strong></p>
        </c:if>
    </center>

    <div class="row">
        <% List<UserDto> users = (List<UserDto>) request.getAttribute("usersList");
            if (users != null && !users.isEmpty()) {
                for (UserDto user : users) { %>
        <center>
            <div class="cardContainer">
                <div class="profile-card">
                    <div class="card-body">
                        <form action="/profile_image" method="get">
                            <div class="product-btn">
                                <input type="submit" class="btn btn-outline-danger" value="View Image">
                            </div>
                        </form>

                        <form action="/users_index" method="post">
                            <h2 class="card-title" style="color: #641515; font-weight: bold; margin-top: 0.5em">User's Info</h2>
                            <p class="card-text" style="color: azure;margin: 0.5em 1em 1em 0;font-weight: inherit;font-size: 1.2em;"><strong>Name: </strong><%= user.getName() %></p>
                            <p class="card-text" style="color: azure;margin: 0.5em 1em 1em 0;font-weight: inherit;font-size: 1.2em;"><strong>Surname: </strong><%= user.getSurname() %></p>
                            <p class="card-text" style="color: azure;margin: 0.5em 1em 1em 0;font-weight: inherit;font-size: 1.2em;"><strong>Age: </strong><%= user.getAge() %></p>
                            <p class="card-text" style="color: azure;margin: 0.5em 1em 1em 0;font-weight: inherit;font-size: 1.2em;"><strong>Address: </strong><%= user.getAddress() %></p>
                            <p class="card-text" style="color: azure;margin: 0.5em 1em 1em 0;font-weight: inherit;font-size: 1.2em;"><strong>Number: </strong><%= user.getNumber() %></p>
                            <p class="card-text" style="color: azure;margin: 0.5em 1em 1em 0;font-weight: inherit;font-size: 1.2em;"><strong>IsAdmin: </strong><%= user.isAdmin() %></p>
                            <input class="card-text" style="    border-radius: 20px;border-color: transparent;background-color: transparent;border-bottom-color: azure;width: 8em;" name="user_email" readonly value="<%= user.getEmail() %>">

                            <div class="submit-btn">
                                <input class="btn btn-outline-danger" type="submit" value="Ban User">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </center>
        <% }
        } else { %>
        <div class="col-12 text-center">
            <p>No users available.</p>
        </div>
        <% } %>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
