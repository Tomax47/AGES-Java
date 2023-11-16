package org.AGES.repository.product;

import org.AGES.dto.ProductAddForm;
import org.AGES.model.Product;
import java.sql.SQLException;

public class ProductRegistrationServiceImpl implements ProductRegistrationService{
    private ProductCRUDRepo productCRUDRepo;


    public ProductRegistrationServiceImpl(ProductCRUDRepo productCRUDRepo){
        this.productCRUDRepo = productCRUDRepo;
    }
    @Override
    public void addProduct(ProductAddForm productAddForm) throws SQLException {
        Product product = Product.builder()
                .productName(productAddForm.getProductName())
                .productDescription(productAddForm.getProductDescription())
                .price(productAddForm.getPrice())
                .sellerId(productAddForm.getSellerId())
                .productImage(productAddForm.getProductImage())
                .build();

        System.out.println("PRODUCT HAS BEEN BUILT, FORWARDING TO CRUD.SAVE!");
        productCRUDRepo.save(product);
    }
}
