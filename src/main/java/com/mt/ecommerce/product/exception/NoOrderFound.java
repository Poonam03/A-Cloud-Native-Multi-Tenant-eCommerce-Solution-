package com.mt.ecommerce.product.exception;

/** * Exception thrown when no order is found.
 */
public class NoOrderFound extends RuntimeException {
    /** * Constructs a new NoOrderFound exception with the specified detail message.
     *
     * @param message the detail message
     */
    public NoOrderFound(String message) {
        super(message);
    }
}
