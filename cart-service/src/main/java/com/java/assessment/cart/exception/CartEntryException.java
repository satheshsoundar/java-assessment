package com.java.assessment.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Entry Already Available")
public class CartEntryException extends Exception {
    public CartEntryException(String message) {
        super(message);
    }
}
