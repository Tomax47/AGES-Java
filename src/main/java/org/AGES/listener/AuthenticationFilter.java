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

        //Checking the user's auth-state
        if (sessionExists) {
            isAuthenticated = (Boolean) session.getAttribute("authenticated");
            if (isAuthenticated == null) {
                isAuthenticated = false;
            }
        }

        //Disallowing the logged-in user from reaching the login\registration pages
        if (isAuthenticated && isLoginPage || isAuthenticated && isRegistrationPage) {
            System.out.println("USER IS LOGGED IN, REDIRECTING HOME");
            response.sendRedirect("/");

            //Allowing the request to the destination
        } else if (isAuthenticated && !isLoginPage || !isAuthenticated && isLoginPage ||
                isAuthenticated && !isRegistrationPage || !isAuthenticated && isRegistrationPage) {
            System.out.println("FILTER : REDIRECTING TO THE REQUESTED PAGE! Destination -> "+ request.getRequestURI());
            filterChain.doFilter(request, response);

            //Not logged-in user, redirecting to the login page |It's possible for the user to access the registration page as well|
        } else {
            System.out.println("USER AINT LOGGED IN, REDIRECTING TO THE LOGIN PAGE");
            response.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }

}
