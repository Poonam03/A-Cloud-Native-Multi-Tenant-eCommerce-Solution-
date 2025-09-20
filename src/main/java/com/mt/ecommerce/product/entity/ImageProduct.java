package com.mt.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "image_product_mt_t")
@Data
public class ImageProduct {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @Column(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_id", nullable = false)
    private UUID imageId;

}
