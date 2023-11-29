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

    <c:if test="${ NoUserProfileImageError != null }">
        <p style="color: #dc3545; font-weight: inherit;"><strong>${ NoUserProfileImageError }</strong></p>
    </c:if>

    <c:if test="${user != null}">
        <div class="cardContainer">
            <div class="profile-card">
                <form action="/profile_image" method="get">
                    <div class="product-btn">
                        <input type="submit" class="btn btn-outline-danger" value="View Image">
                    </div>
                </form>

                <form action="/profile" method="post" enctype="multipart/form-data">
                    <input class="btn btn-outline-dark" type="file" name="image">

                    <div class="mb-3 row">
                        <div class="email-label">
                            <label class="input-label">name</label>
                        </div>
                        <div class="email-div">
                            <input type="text" name="name" value="${user.name}" style="border-radius: 20px; border-color: transparent;background-color: transparent; border-bottom-color: azure; width: 8em; color: azure">
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <div class="email-label">
                            <label class="input-label">Surname</label>
                        </div>
                        <div class="email-div">
                            <input type="text" name="surname" value="${user.surname}" style="border-radius: 20px; border-color: transparent;background-color: transparent; border-bottom-color: azure; width: 8em; color: azure"">
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <div class="email-label">
                            <label class="input-label">Age</label>
                        </div>
                        <div class="email-div">
                            <input type="text" name="age" value="${user.age}" style="border-radius: 20px; border-color: transparent;background-color: transparent; border-bottom-color: azure; width: 8em; color: azure"">
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <div class="email-label">
                            <label class="input-label">Number</label>
                        </div>
                        <div class="email-div">
                            <input type="text" name="number" value="${user.number}" style="border-radius: 20px; border-color: transparent;background-color: transparent; border-bottom-color: azure; width: 8em; color: azure"">
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <div class="email-label">
                            <label class="input-label">Address</label>
                        </div>
                        <div class="email-div">
                            <input type="text" name="address" value="${user.address}" style="border-radius: 20px; border-color: transparent;background-color: transparent; border-bottom-color: azure; width: 8em; color: azure"">
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <div class="email-label">
                            <label class="input-label">Email</label>
                        </div>
                        <div class="email-div">
                            <input type="text" name="email" readonly value="${user.email}" style="border-radius: 20px; border-color: transparent;background-color: transparent; border-bottom-color: azure; width: 8em; color: azure"">
                        </div>
                    </div>

                    <div class="login-btn">
                        <input type="submit" class="btn btn-outline-danger" value="Update">
                    </div>
                </form>
                <a class="delete-profile" href="/delete_account" style="color: #dc3545; text-decoration: none;">Delete My Account</a>
            </div>
        </div>
    </c:if>

    <c:if test="${user.getRole().equals(String.valueOf('Admin'))}">
        <a class="admin-panel-link" aria-current="page" href="/users_index">Admin Panel</a>
    </c:if>
</center>
<%@include file="footer.jsp"%>
</body>
</html>
