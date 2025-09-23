package com.mt.ecommerce.product.exception;

public class NoOrderFound extends RuntimeException {
    public NoOrderFound(String message) {
        super(message);
    }
}
