package com.mt.ecommerce.product.model;

import java.util.List;
import java.util.UUID;

/** * Represents a category in the e-commerce system.
 */
public class CategoryBO {

    private String name;

    private String slug;

    private String description;

    private List<ImageBO> imageBOS;

    private UUID id;

    private boolean active;

    private UUID vendorID;

    public UUID getVendorID() {
        return vendorID;
    }

    public void setVendorID(UUID vendorID) {
        this.vendorID = vendorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageBO> getImageBOS() {
        return imageBOS;
    }

    public void setImageBOS(List<ImageBO> imageBOS) {
        this.imageBOS = imageBOS;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
