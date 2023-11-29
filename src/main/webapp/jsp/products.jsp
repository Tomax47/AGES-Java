<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="org.AGES.model.Product" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Tom
  Date: 11/10/2023
  Time: 9:25 PM
  To change this template use FileInfo | Settings | FileInfo Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products</title>
  <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>

<center>
  <div class="container mt-4">
    <h1 class="text-center">Products</h1>

    <div class="row">
      <% List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
          for (Product product : products) { %>
      <div class="cardContainer">
        <div class="card">
          <form action="/product_image" method="get">
            <div class="product-btn">
              <input type="hidden" name="product_id" value="<%= product.getId() %>">
              <input type="submit" class="btn btn-outline-danger" value="View Product">
            </div>
          </form>

          <form action="/products" method="post">
            <h2 class="product-name-card" style="color: #dc3545;font-weight: bold;font-size: 2em;"><%= product.getProductName() %></h2>
            <p class="details-card" style="color: azure;font-size: 1em;font-weight: bold;">Description</p>
            <p class="text-card" style="color: azure; text-overflow: ellipsis; width: 20em"><%= product.getProductDescription() %></p>
            <p class="text-card" style="color: azure;"><strong>Price: $</strong><%= product.getPrice() %></p>
            <input type="hidden" name="product_id" value="<%= product.getId() %>">
            <% if (product.getSellerId() != (Long) session.getAttribute("userId")) { %>
            <input class="btn btn-outline-danger" type="submit" value="Buy">
            <% } else { %>
            <a href="/published_products">Check In Dashboard</a>
            <% } %>
          </form>
        </div>
      </div>
      <% }
      } else { %>
      <div class="col-12 text-center">
        <p>No products available.</p>
      </div>
      <% } %>
    </div>
  </div>
</center>
<%@include file="footer.jsp"%>
</body>
</html>
