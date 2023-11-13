package org.AGES.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private long id;
    private String productName;
    private String productDescription;
    private double price;
//    private double rating; //TODO: ADD THE FEATURE
    private byte[] productImage;
    private long sellerId;
}
