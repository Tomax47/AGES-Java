package org.AGES.servlet.product;

import org.AGES.dto.ProductAddForm;
import org.AGES.repository.file.FileRWService;
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
    private ProductRegistrationService productRegistrationService;
    private FileRWService fileRWService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productRegistrationService = (ProductRegistrationService) servletContext.getAttribute("ProductRegistrationService");
        fileRWService = (FileRWService) servletContext.getAttribute("fileRWService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/new_product.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductAddForm productAddForm = new ProductAddForm();

        //Getting the id of the user that's currently logged in!
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");

        productAddForm.setProductName(req.getParameter("product_name"));
        System.out.println(productAddForm.getProductName());
        productAddForm.setProductDescription(req.getParameter("product_description"));
        productAddForm.setPrice(Double.parseDouble(req.getParameter("price")));
        productAddForm.setSellerId(userId);


        // Image fetching
        Part part = req.getPart("image");

        try {
            Long productId = productRegistrationService.addProduct(productAddForm);
            if (productId != null) {
                //Saving the product's image!
                fileRWService.saveFileToStorage(part.getInputStream(), part.getSubmittedFileName(), part.getContentType(), part.getSize(), null, productId);
            }
            resp.sendRedirect("/");
        } catch (SQLException e) {
            //TODO: HANDLE IN A BETTER WAY
            throw new RuntimeException("Error adding the product : " + e.getMessage(), e);
        }
    }
}
