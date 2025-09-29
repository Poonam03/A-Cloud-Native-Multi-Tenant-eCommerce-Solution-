package com.mt.ecommerce.product.exception;

/** * Exception thrown when an error occurs during image upload.
 */
public class ImageUploadException extends MTException{

    /**
     * Constructs a new ImageUploadException with the specified detail message.
     *
     * @param message the detail message
     */
    public ImageUploadException(String message) {
        super(message);
    }

    /**
     * Constructs a new ImageUploadException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
