package org.AGES.servlet.product;

import org.AGES.model.Product;
import org.AGES.repository.product.ProductCRUDRepo;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products")
public class ProductsIndexServLet extends HttpServlet {
    private ProductCRUDRepo productCRUDRepo;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productCRUDRepo = (ProductCRUDRepo) servletContext.getAttribute("ProductCRUDRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productCRUDRepo.findAll();

        System.out.println("Is products' list null? -> "+products.isEmpty());
        req.setAttribute("products", products);
        req.getRequestDispatcher("jsp/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("product_id"));
        //Temp saving the product
        Product product = productCRUDRepo.findById(productId);

        //Checking user's authentication status
        boolean isAuthenticated = (Boolean) req.getSession().getAttribute("authenticated");

        if (isAuthenticated) {
            //User is authorized, carrying the request on
            System.out.println("USER IS AUTHENTICATED, CARRYING ON THE DELETE/SAVE SOLD PRODUCT");
            Long userId = (Long) req.getSession().getAttribute("userId");

            //Deleting the product from the products table
            int deleteProductFromProducts = productCRUDRepo.deleteProductWhenBuy(productId);

            if (deleteProductFromProducts == 1) {
                //If deletion completed
                try {
                    //Saving the product to the sold_products table in the DB
                    if (productCRUDRepo.saveToSoldProducts(product, userId) == 1) {
                        resp.sendRedirect("/products");
                        System.out.println("PRODUCT HAS BEEN ADDED TO SOLD PRODUCTS");
                    } else {
                        resp.sendRedirect("/products");
                        System.out.println("PRODUCT AIN'T BEEN ADDED TO SOLD PRODUCTS");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("PRODUCT AINT DELETED FROM THE PRODUCTS TABLE");
            }
        } else {
            //User ain't authorized, redirecting to the login page
            resp.sendRedirect("/login");
        }
    }
}
