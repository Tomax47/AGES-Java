<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="org.AGES.model.Product" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 11/10/2023
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
  <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>

<div class="container mt-4">
  <h1 class="text-center">Products</h1>

  <div class="row">
    <% List<Product> products = (List<Product>) request.getAttribute("products");
      if (products != null && !products.isEmpty()) {
        for (Product product : products) { %>
    <center>
      <form action="/products" method="post">
        <div class="col-md-4 mb-4">
          <div class="card">
            <div class="card-body">
              <img src="data:image/jpeg;base64,<%= product.getProductImage() %>" alt="User Image" width="150" height="150"><br />

              <h2 class="card-title" style="color: #641515; font-weight: bold"><%= product.getProductName() %></h2>
              <p><strong>Description</strong></p>
              <p class="card-text"><%= product.getProductDescription() %></p>
              <p class="card-text"><strong>Price: $</strong><%= product.getPrice() %></p>
              <input type="hidden" value="<%= product.getId() %>">
              <input class="btn btn-outline-danger" type="submit" value="Buy">
            </div>
          </div>
        </div>
      </form>

    </center>
    <% }
    } else { %>
    <div class="col-12 text-center">
      <p>No products available.</p>
    </div>
    <% } %>
  </div>
</div>

</body>
</html>
