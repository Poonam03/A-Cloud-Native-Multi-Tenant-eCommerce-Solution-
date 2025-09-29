package com.mt.ecommerce.product.model;

import java.util.UUID;

/** * Represents an image associated with a product or category.
 */
public class ImageBO {

    public ImageBO() {
    }

    private UUID id;

    private String imageUrl;

    private String altText;

    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }
}
