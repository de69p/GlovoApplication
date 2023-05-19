package com.example.glovoapplication.exception;

import org.springframework.data.crossstore.ChangeSetPersister;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
