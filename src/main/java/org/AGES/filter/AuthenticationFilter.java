package org.AGES.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        Boolean isAuthenticated = false;
        boolean sessionExist = session != null;
        boolean isLoginPage = req.getRequestURI().equals("/login");
        boolean isRegistrationPage =req.getRequestURI().equals("/register");

        //Checking for the seesion
        if (sessionExist) {
            isAuthenticated = (Boolean) session.getAttribute("authenticated");
            if (isAuthenticated = null) {
                isAuthenticated = false;
            }
        }

        //Redirecting based on the req target and the authentication state!
        if (isAuthenticated && !isLoginPage || !isAuthenticated && isLoginPage ||
                isAuthenticated && !isRegistrationPage || !isAuthenticated && isRegistrationPage) {
            filterChain.doFilter(req,resp);

            //checks
            System.out.println("IS HEADING TO THE REGISTRATION PAGE : "+isRegistrationPage);
            System.out.println("FILTER : allowing user's req to the page!");

        } else if (isAuthenticated && isLoginPage || isAuthenticated && isRegistrationPage) {
            System.out.println("FILTER : dis-allowing user's req to the login page -> user is already logged in!!");
            resp.sendRedirect("/");

        } else {
            System.out.println("FILTER : dis-allowing user's req to the page -> user is not logged in!!");
            resp.sendRedirect("login");
        }
    }

    @Override
    public void destroy() {

    }

}
