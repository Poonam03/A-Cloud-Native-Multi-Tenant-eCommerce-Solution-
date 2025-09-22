package com.mt.ecommerce.product.exception;

public class CategoryNotFoundException extends MTException{
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}