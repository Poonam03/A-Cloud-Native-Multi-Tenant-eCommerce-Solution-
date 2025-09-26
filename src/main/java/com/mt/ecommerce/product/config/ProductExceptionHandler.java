package com.mt.ecommerce.product.config;

import com.mt.ecommerce.product.exception.CategoryNotFoundException;
import com.mt.ecommerce.product.exception.NoOrderFound;
import com.mt.ecommerce.product.exception.ProductNotFoundException;
import com.mt.ecommerce.product.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class, IllegalArgumentException.class, NoOrderFound.class, ProductNotFoundException.class})
    public ResponseEntity<Error> handleNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(new Error(404, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Error> error(Exception ex) {
        return new ResponseEntity<>(new Error(500, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
