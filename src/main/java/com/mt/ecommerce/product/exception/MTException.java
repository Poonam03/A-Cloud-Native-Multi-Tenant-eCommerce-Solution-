package com.mt.ecommerce.product.exception;

/** * Base exception class for the e-commerce product module.
 */
public class MTException extends RuntimeException{


    /** * Constructs a new MTException with the specified detail message.
     *
     * @param message the detail message
     */
    public MTException(String message) {
        super(message);
    }

    /** * Constructs a new MTException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public MTException(String message, Throwable cause) {
        super(message, cause);
    }
}
