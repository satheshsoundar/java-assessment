package com.java.assessment.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Cart Not Found")
public class CartNotFoundException extends Exception {
    public CartNotFoundException(String message) {
        super(message);
    }
}
