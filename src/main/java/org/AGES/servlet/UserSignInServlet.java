package org.AGES.servlet;

import org.AGES.repository.UserCRUDRepo;
import org.AGES.repository.UserRegistrationService;
import org.postgresql.jdbc.UUIDArrayAssistant;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
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
        req.getRequestDispatcher("jsp/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            if (userCRUDRepo.login(email,password)) {
                //Open a new session
                HttpSession httpSession = req.getSession(true);
                httpSession.setAttribute("authenticated",true);

                String loginCookieUUID = UUID.randomUUID().toString();
                //TODO: INSERT THE UUID INTO THE DATABASE WITH THE USER_ID
                //ADD METHODS TO GET THE USER_ID

                Cookie loginCookie = new Cookie("login","authenticated");
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

}
