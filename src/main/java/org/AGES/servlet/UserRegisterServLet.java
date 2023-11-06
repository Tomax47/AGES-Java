package org.AGES.servlet;

import org.AGES.dto.RegisterForm;
import org.AGES.repository.UserCRUDRepo;
import org.AGES.repository.UserRegistrationService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/register")
public class UserRegisterServLet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ancient_goods_estore";

    private UserCRUDRepo userCRUDRepo;
    private UserRegistrationService userRegistrationService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userCRUDRepo = (UserCRUDRepo) servletContext.getAttribute("UserCRUDRepo");
        userRegistrationService = (UserRegistrationService) servletContext.getAttribute("UserRegistrationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setName(req.getParameter("name"));
        registerForm.setSurname(req.getParameter("surname"));
        registerForm.setEmail(req.getParameter("email"));
        registerForm.setPassword(req.getParameter("password"));

        try {
            userRegistrationService.register(registerForm);
            resp.sendRedirect("/login");
        } catch (SQLException e) {
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }
}
