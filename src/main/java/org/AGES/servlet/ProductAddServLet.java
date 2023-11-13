package org.AGES.servlet;

import org.AGES.dto.ProductAddFrom;
import org.AGES.repository.product.ProductRegistrationService;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,
        maxRequestSize = 1024 * 1024 * 3
)

@WebServlet("/new_product")
public class ProductAddServLet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ancient_goods_estore";
    private ProductRegistrationService productRegistrationService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productRegistrationService = (ProductRegistrationService) servletContext.getAttribute("ProductRegistrationService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/new_product.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductAddFrom productAddFrom = new ProductAddFrom();

        //Getting the id of the user that's currently logged in!
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");

        productAddFrom.setProductName(req.getParameter("product_name"));
        System.out.println(productAddFrom.getProductName());
        productAddFrom.setProductDescription(req.getParameter("product_description"));
        productAddFrom.setPrice(Double.parseDouble(req.getParameter("price")));
        productAddFrom.setSellerId(userId);


        //Fetching the image
        Part filePart = req.getPart("image");
        System.out.println(filePart.getName());

        byte[] image = null;

        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();
            image = new byte[(int) filePart.getSize()];
            inputStream.read(image);
        }

        productAddFrom.setProductImage(image);

        System.out.println("Product registration form completed -> Calling addProduct method");
        try {
            productRegistrationService.addProduct(productAddFrom);
            resp.sendRedirect("/");
        } catch (SQLException e) {
            //TODO: HANDLE IN A BETTER WAY
            throw new RuntimeException("Error adding the product : " + e.getMessage(), e);
        }
    }
}
