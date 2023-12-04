<nav class="mask">
  <a class="site-logo" href="/" style="color: #dc3545">AGES</a>
  <a href="/products">Products</a>
  <%
    if (session == null || session.getAttribute("authenticated") == null) {
  %>
  <a href="login">Sign In</a>
  <a href="register">Register</a>
  <% } else { %>
  <a href="profile">Profile</a>
  <a href="products">Market</a>
  <a href="/published_products">My Products</a>
  <a href="/purchased_products">Purchased Products</a>
  <a href="new_product">Sell Something</a>
  <form action="/logout" method="post">
    <button class="logout-btn" type="submit">logout</button>
  </form>
  <% } %>
</nav>