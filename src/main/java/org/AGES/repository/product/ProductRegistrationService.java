package org.AGES.repository.product;

import org.AGES.dto.ProductAddForm;

import java.sql.SQLException;

public interface ProductRegistrationService {
    Long addProduct(ProductAddForm productAddForm) throws SQLException;
}
