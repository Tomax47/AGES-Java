<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">AGES</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/">Home</a>
        </li>
        <!-- Checking the user's auth-state for reviewing the login & register options -->
        <%
          if (session == null || session.getAttribute("authenticated") == null) {
        %>
        <li class="nav-item">
          <a class="nav-link" href="login">Sign In</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="register">Register</a>
        </li>
        <% } else { %>
      </ul>
      <form class="d-flex" action="/logout" method="post">
        <button class="btn btn-outline-dark" type="submit">logout</button>
      </form>
      <% } %>

    </div>
  </div>
</nav>

