package com.mt.ecommerce.product.model;

import java.util.UUID;

/** * Represents a payment transaction associated with an order.
 */
public class PaymentBO {

    private UUID id;
    private UUID orderId;

    private Double amount;

    private UUID vendorId;

    private String userName;

    public UUID getVendorId() {
        return vendorId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PaymentBO() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
