package com.mt.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "image_category_mt_t")
@Data
public class ImageCategory {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @Column(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "image_id", nullable = false)
    private UUID imageId;

}
