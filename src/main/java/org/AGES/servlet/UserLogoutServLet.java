package org.AGES.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class UserLogoutServLet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Invalidating the current session
        HttpSession session = req.getSession();
        session.removeAttribute("authenticated");
        session.removeAttribute("userId");
        session.invalidate();

        //Removing the loginCookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login")) {
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                    System.out.println("Login-Cookie has been removed!");
                }
            }
        }

        resp.sendRedirect("/login");
    }
}
