package com.mt.ecommerce.product.exception;

/** * Exception thrown when a requested product is not found.
 */
public class ProductNotFoundException extends MTException{
    /** * Constructs a new ProductNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

}
