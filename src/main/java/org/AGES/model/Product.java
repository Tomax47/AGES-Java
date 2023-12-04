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
    private long sellerId;
}
