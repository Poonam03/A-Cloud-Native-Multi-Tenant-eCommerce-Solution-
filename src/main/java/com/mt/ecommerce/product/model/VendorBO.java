package com.mt.ecommerce.product.model;

import java.util.UUID;

public class VendorBO {

    public VendorBO() {
    }

    private UUID id;

    private String storeName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
