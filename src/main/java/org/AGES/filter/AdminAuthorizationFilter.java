package org.AGES.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/users_index")
public class AdminAuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Boolean isAdmin = false;
        Boolean sessionExists = session != null;
        Boolean isUsersPage = request.getRequestURI().equals("/users_index");

        //Checking if the user is an Admin
        if (sessionExists) {
            isAdmin = (Boolean) session.getAttribute("Admin");
            if (isAdmin == null) {
                isAdmin = false;
            }
        }

        if (isAdmin) {
            System.out.println("USER IS AN ADMIN -> GRANTED THE ADMIN PASS!");
            filterChain.doFilter(request, response);
        } else if (!isAdmin && isUsersPage) {
            System.out.println("USER WITHOUT ADMIN PRIVILEGES -> DENIED THE ADMIN PASS!");
            response.sendRedirect("/");
        } else {
            System.out.println("NO ADMIN PRIVILEGES NEEDED -> GIVE THE PASS!");
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
