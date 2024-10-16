package com.dipakraut.eCommerce.exception.Product;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String message) {
        super(message);
    }

}
