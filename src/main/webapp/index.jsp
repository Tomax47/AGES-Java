
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AGES</title>
   <%@include file="all_components/allCss.jsp"%>
</head>
<body>
<%@include file="jsp/navbar.jsp"%>
<h1>
    <div>
        WELCOME TO AGES
    </div>
    <div>
        <strong class="store-name">Ancient Goods E<span style="color: #dc3545">-</span>Store</strong>
    </div>
</h1>

<center>
    <section class="introduction-section">
        <div class="extra-info"></div>
        <h2 class="intro-section-header">About AGES</h2>
        <p class="intro-paragraph">AGES is an online store, where people can sell & buy ancient goods dating back to the mid-ages and beyond!</p>

        <div class="call-to-action-div">
            <a class="intro-call-to-action" href="/products">Browse The Products</a>
        </div>
        <div class="extra-info"></div>
    </section>

    <section class="seller-section">
        <div class="extra-info"></div>
        <h2 class="seller-section-header">Who Can Sell On AGES?</h2>
        <p class="seller-paragraph">AGES welcomes anyone! all you need to do to become a seller on AGES is simply creating an account</p>

        <div class="call-to-action-div">
            <a class="seller-call-to-action" href="/new_product">Become A Seller</a>
        </div>
        <div class="extra-info"></div>
    </section>

</center>
<%@include file="jsp/footer.jsp"%>
</body>
</html>