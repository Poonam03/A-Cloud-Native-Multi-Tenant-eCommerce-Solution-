package com.mt.ecommerce.product.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductBO {

    private UUID id;

    private String name;

    private String slug;

    private String description;

    private String sku;

    private Double price;

    private String quantity;

    private List<Image> images;


}
