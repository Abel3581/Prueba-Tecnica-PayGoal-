package com.test.products.exception;

public class ProductAlreadyExistsException extends RuntimeException{

    public ProductAlreadyExistsException(String message){
        super(message);
    }
}
