package com.mt.ecommerce.product.entity;

import jakarta.persistence.*;

import java.util.UUID;

/** * Entity class representing the association between Images and Products.
 */
@Entity
@Table(name = "image_product_mt_t")
public class ImageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID identification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Product product;

    @Column(name = "image_id", nullable = false)
    private UUID imageId;

    public UUID getIdentification() {
        return identification;
    }

    public void setIdentification(UUID identification) {
        this.identification = identification;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }
}
