package org.AGES.servlet;

import org.AGES.repository.UserCRUDRepo;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

@WebServlet("/login")
public class UserSignInServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ancient_goods_estore";

    private UserCRUDRepo userCRUDRepo;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userCRUDRepo = (UserCRUDRepo) servletContext.getAttribute("UserCRUDRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();

        //Checking for an existence of the loginCookie in the cookies list
        if (cookies != null && Arrays.stream(cookies).anyMatch(cookie -> {
            return cookie.getName().equals("login");
        })) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login")) {
                    String uuid = cookie.getValue();
                    try {
                        //Checking the userId by the loginCookieUUID
                        long userId = userCRUDRepo.checkUserExistenceByLoginCookie(uuid);
                        if (userId != 0) {
                            HttpSession session = req.getSession();
                            session.setAttribute("authenticated", true);
                            session.setAttribute("userId",userId);

                            System.out.println("\n\nLOGGED THE USER IN USING THE LOGIN COOKIE!!!");
                        }
                    } catch (SQLException e) {
                        //TODO: HANDLE THE EXCEPTION!
                    }
                }
            }
        } else {
            req.getRequestDispatcher("jsp/login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            if (userCRUDRepo.login(email,password)) {
                //Opening a new session
                HttpSession httpSession = req.getSession(true);

                //Setting session's attr
                long userId = userCRUDRepo.getUserId(email);
                if (userId != 0) {
                    httpSession.setAttribute("userId", userId);
                    httpSession.setAttribute("authenticated",true);
                }

                //Saving the userId & the LoginCookieUUID into the DB
                String loginCookieUUID = UUID.randomUUID().toString();
                System.out.println("CHECKING : \nLoginCookieUUID -> "+loginCookieUUID+"\nUserID -> "+getUserIdFromSession(resp, httpSession));
                userCRUDRepo.saveUserLoginCookie(loginCookieUUID, getUserIdFromSession(resp, httpSession));

                //Initializing the login cookie
                Cookie loginCookie = new Cookie("login",loginCookieUUID);
                resp.addCookie(loginCookie);
                loginCookie.setMaxAge(300);

                resp.sendRedirect("/");
            } else {
                resp.sendRedirect("/login");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error logging the user in : " + e.getMessage(), e);
        }
    }


    //Getting the userId. START
    private long getUserIdFromSession(HttpServletResponse resp, HttpSession session) throws IOException {
        if (session != null && session.getAttribute("authenticated") != null) {
            return getLoggedInUserId(session);
        } else {
            resp.sendRedirect("/");
            throw new IllegalStateException("User is not logged in.");
        }
    }

    private long getLoggedInUserId(HttpSession session) {
        Object userIdObject = session.getAttribute("userId");

        if (userIdObject != null) {
            try {
                return Long.parseLong(userIdObject.toString());
            } catch (NumberFormatException e) {
                throw new IllegalStateException("User ID in session is not a valid number.");
            }
        } else {
            throw new IllegalStateException("User ID is not found in session.");
        }
    }
    //END

}
