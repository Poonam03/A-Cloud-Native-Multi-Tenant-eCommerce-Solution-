package com.mt.ecommerce.product.exception;

/** * Exception thrown when a requested category is not found.
 */
public class CategoryNotFoundException extends MTException{
    /**
     * Constructs a new CategoryNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new CategoryNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}