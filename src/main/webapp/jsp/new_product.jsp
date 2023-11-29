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
    <h1>New Product</h1>
    <div class="cardContainer">
        <div class="card">
            <p class="login-header">Fill Out Product's Details</p>
            <form action="/new_product" method="post" enctype="multipart/form-data">
                <div class="image-field">
                    <label class="input-label">Image :</label>
                    <div class="field">
                        <input class="btn btn-outline-dark" type="file" name="image">
                    </div>
                </div>
                <div class="section">
                    <label class="input-label">Name :</label>
                    <div class="field">
                        <input type="text" class="input-div" name="product_name">
                    </div>
                </div>
                <div class="section">
                    <label class="input-label">Description :</label>
                    <div class="field">
                        <input type="text" class="input-div" name="product_description">
                    </div>
                </div>
                <div class="section">
                    <label class="input-label">Price :</label>
                    <div class="field">
                        <input type="text" class="input-div" name="price">
                    </div>
                </div>
                <div class="login-btn">
                    <input type="submit" class="btn btn-outline-danger" value="Sell">
                </div>
            </form>
        </div>
    </div>
</center>
<%@include file="footer.jsp"%>
</body>
</html>