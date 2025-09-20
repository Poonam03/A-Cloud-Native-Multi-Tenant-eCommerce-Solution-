package com.mt.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product-mt-t")
@Data
public class Product {

    @Id
    private UUID id;

    @Column(name = "vendor_id", nullable = false)
    private UUID vendorId;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    private String name;

    private String slug;

    private String description;

    private String sku;

    private Double price;

    @Column(name = "image_id")
    private UUID images;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ImageProduct> imageProducts;

    @Column(name = "quantity")
    private int stockQuantity;

    private boolean active;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_time")
    private LocalDateTime modifiedAt;





}
