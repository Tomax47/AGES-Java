package org.AGES.repository.product;

import org.AGES.dto.ProductAddFrom;
import org.AGES.model.Product;
import java.sql.SQLException;

public class ProductRegistrationServiceImpl implements ProductRegistrationService{
    private ProductCRUDRepo productCRUDRepo;


    public ProductRegistrationServiceImpl(ProductCRUDRepo productCRUDRepo){
        this.productCRUDRepo = productCRUDRepo;
    }
    @Override
    public void addProduct(ProductAddFrom productAddFrom) throws SQLException {
        Product product = Product.builder()
                .productName(productAddFrom.getProductName())
                .productDescription(productAddFrom.getProductDescription())
                .price(productAddFrom.getPrice())
                .sellerId(productAddFrom.getSellerId())
                .productImage(productAddFrom.getProductImage())
                .build();

        System.out.println("PRODUCT HAS BEEN BUILT, FORWARDING TO CRUD.SAVE!");
        productCRUDRepo.save(product);
    }
}
