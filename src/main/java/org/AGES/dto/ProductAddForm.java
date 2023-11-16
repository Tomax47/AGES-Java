package org.AGES.dto;

import lombok.Data;

@Data
public class ProductAddForm {
    private long id;
    private String productName;
    private String productDescription;
    private double price;
    //    private double rating; //TODO: ADD THE FEATURE
    private byte[] productImage;
    private long sellerId;
}
