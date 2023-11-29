<%@ page import="org.AGES.model.Product" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Products</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>
<center>

    <h1 class="text-center">Published Products</h1>

    <c:if test="${ DeleteProductError != null }">
        <p style="color: #dc3545; font-weight: inherit;"><strong>${DeleteProductError}</strong></p>
    </c:if>

    <div class="container mt-4">
        <div class="row">
            <% List<Product> products = (List<Product>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (Product product : products) { %>
            <center>
                <div class="cardContainer">
                    <div class="card">
                        <form action="/product_image" method="get">
                            <div class="product-btn">
                                <input type="hidden" name="product_id" value="<%= product.getId() %>">
                                <input type="submit" class="btn btn-outline-danger" value="View Product">
                            </div>
                        </form>

                        <form action="/published_products" method="post">
                            <h2 class="product-name-card" style="color: #dc3545;font-weight: bold;font-size: 2em;"><%= product.getProductName() %></h2>
                            <p class="details-card" style="color: azure;font-size: 1em;font-weight: bold;">Description</p>
                            <p class="text-card" style="color: azure; text-overflow: ellipsis; width: 20em"><%= product.getProductDescription() %></p>
                            <p class="text-card" style="color: azure;"><strong>Price: $</strong><%= product.getPrice() %></p>
                            <input type="hidden" value="<%= product.getId() %>">
                        </form>

                        <form action="/delete_product" method="post">
                            <input type="hidden" name="product_id" value="<%= product.getId() %>">
                            <input class="btn btn-outline-danger" type="submit" value="Delete">
                        </form>
                    </div>
                </div>
            </center>
            <% }
            } else { %>
            <div class="col-12 text-center">
                <p>No products available!</p>
                <div class="seller-call-to-action">
                    <a href="/new_product">Sell Something</a>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</center>
<%@include file="footer.jsp"%>

</body>
</html>
