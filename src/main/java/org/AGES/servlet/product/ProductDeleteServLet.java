package org.AGES.servlet.product;

import org.AGES.repository.file.FileRWService;
import org.AGES.repository.product.ProductCRUDRepo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete_product")
public class ProductDeleteServLet extends HttpServlet {
    private static final String DeleteProductError = "Something went wrong! Cannot delete the product.";
    private ProductCRUDRepo productCRUDRepo;
    private FileRWService fileRWService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productCRUDRepo = (ProductCRUDRepo) servletContext.getAttribute("ProductCRUDRepo");
        fileRWService = (FileRWService) servletContext.getAttribute("fileRWService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.valueOf(req.getParameter("product_id"));
        Long userId = (Long) req.getSession().getAttribute("userId");
        System.out.println("PRODUCT-USER TO DELETE ID -> "+productId+"-"+userId);

        int deleteProduct = productCRUDRepo.deleteProduct(productId, userId);

        if (deleteProduct == 1) {

            try {
                fileRWService.deleteProductImage(productId);
            } catch (SQLException e) {
                //Product's image ain't been deleted!
                req.getSession().setAttribute("DeleteProductError", DeleteProductError);
                resp.sendRedirect("/published_products");
            }
            //Deletion went fine!
            resp.sendRedirect("/published_products");
        } else {
            //Product ain't deleted
            req.getSession().setAttribute("DeleteProductError", DeleteProductError);
            resp.sendRedirect("/published_products");
        }
    }

}
