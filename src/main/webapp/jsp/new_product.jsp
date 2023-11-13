<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Product</title>
    <%@include file="/all_components/allCss.jsp"%>
</head>
<body>
<%@include file="navbar.jsp" %>
<br><br>
<center>
    <h1>Fill Out The Product's Details</h1>
    <form action="/new_product" method="post" enctype="multipart/form-data">
        <div class="image-field">
            <label class="label">Image :</label>
            <input class="btn btn-outline-dark" type="file" name="image">
        </div>
        <div class="section">
            <label class="label">Name :</label>
            <div class="field">
                <input type="text" name="product_name">
            </div>
        </div>
        <div class="section">
            <label class="label">Description :</label>
            <div class="field">
                <input type="text" name="product_description">
            </div>
        </div>
        <div class="section">
            <label class="label">Price :</label>
            <div class="field">
                <input type="text" name="price">
            </div>
        </div>
        <div class="product-btn">
            <input type="submit" class="btn btn-outline-danger" value="Add">
        </div>
    </form>
</center>

</body>
</html>
