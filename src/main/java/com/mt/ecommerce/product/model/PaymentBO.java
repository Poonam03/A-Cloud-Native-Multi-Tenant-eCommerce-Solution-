package com.mt.ecommerce.product.model;

import java.util.UUID;

public class PaymentBO {

    private UUID orderId;

    private Double amount;

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
