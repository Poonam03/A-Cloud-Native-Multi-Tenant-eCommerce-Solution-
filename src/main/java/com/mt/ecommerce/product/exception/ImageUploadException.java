package com.mt.ecommerce.product.exception;

public class ImageUploadException extends MTException{

    public ImageUploadException(String message) {
        super(message);
    }

    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
