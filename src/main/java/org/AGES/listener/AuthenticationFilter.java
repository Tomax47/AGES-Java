package org.AGES.listener;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Boolean isAuthenticated = false;
        Boolean sessionExists = session != null;
        Boolean isLoginPage = request.getRequestURI().equals("/login");
        Boolean isRegistrationPage = request.getRequestURI().equals("/register");

        if (sessionExists) {
            isAuthenticated = (Boolean) session.getAttribute("authenticated");
            if (isAuthenticated == null) {
                isAuthenticated = false;
            }
        }

        if (isAuthenticated && isLoginPage || isAuthenticated && isRegistrationPage) {
            System.out.println("USER IS LOGGED IN, REDIRECTING HOME");
            response.sendRedirect("/");

        } else if (isAuthenticated && !isLoginPage || !isAuthenticated && isLoginPage ||
                isAuthenticated && !isRegistrationPage || !isAuthenticated && isRegistrationPage) {
            System.out.println("FILTER : REDIRECTING TO THE REQUESTED PAGE!");
            filterChain.doFilter(request, response);

        } else {
            System.out.println("USER AINT LOGGED IN, REDIRECTING TO THE LOGIN PAGE");
            response.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }

}
