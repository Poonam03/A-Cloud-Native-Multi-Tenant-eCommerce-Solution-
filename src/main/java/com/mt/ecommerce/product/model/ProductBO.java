package com.mt.ecommerce.product.model;

import java.util.List;
import java.util.UUID;

public class ProductBO {

    public ProductBO() {
    }

    private UUID id;

    private String name;

    private String slug;

    private String description;

    private String sku;

    private Double price;

    private Integer quantity;

    private List<ImageBO> imageBOS;

    private UUID vendorId;

    private UUID categoryId;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public List<ImageBO> getImageBOS() {
        return imageBOS;
    }

    public void setImageBOS(List<ImageBO> imageBOS) {
        this.imageBOS = imageBOS;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<ImageBO> getImages() {
        return imageBOS;
    }

    public void setImages(List<ImageBO> imageBOS) {
        this.imageBOS = imageBOS;
    }
}
