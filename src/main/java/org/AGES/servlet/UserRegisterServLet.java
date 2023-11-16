package org.AGES.servlet;

import org.AGES.dto.RegisterForm;
import org.AGES.repository.user.UserCRUDRepo;
import org.AGES.repository.user.UserRegistrationService;
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
    private static final String nullRegistrationValuesErrorMsg = "None of the fields must be left blank!";
    private static final String blackListedEmailError = "Can't use this email for registration, please use another email!";

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
            int isUserRegistered = userRegistrationService.register(registerForm);
            if (isUserRegistered == 1) {
                //Success
                resp.sendRedirect("/login");
            } else if (isUserRegistered == 2) {
                //Failure -> Blacklisted
                req.getSession().setAttribute("registerErrorMessage", blackListedEmailError);
                resp.sendRedirect("/register");
            } else {
                //Failure -> Other Errors
                req.getSession().setAttribute("registerErrorMessage", nullRegistrationValuesErrorMsg);
                resp.sendRedirect("/register");
            }
        } catch (SQLException e) {
            //TODO: HANDLE IN A BETTER WAY
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }
}
