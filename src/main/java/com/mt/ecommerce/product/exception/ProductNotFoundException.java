package com.mt.ecommerce.product.exception;

public class ProductNotFoundException extends MTException{
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
