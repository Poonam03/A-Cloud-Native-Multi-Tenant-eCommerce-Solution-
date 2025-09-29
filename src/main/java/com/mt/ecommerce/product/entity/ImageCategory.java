package com.mt.ecommerce.product.entity;

import jakarta.persistence.*;

import java.util.UUID;

/** * Entity class representing the association between Images and Categories.
 */
@Entity
@Table(name = "image_category_mt_t")
public class ImageCategory {

    @Id
    @Column(name = "identification")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Category category;

    @Column(name = "image_id", nullable = false)
    private UUID imageId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }
}
