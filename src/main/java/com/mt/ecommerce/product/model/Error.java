package com.mt.ecommerce.product.model;

/** * Represents an error with an error code and message.
 */
public class Error {

    int errorCode;

    String message;

    public Error(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
