package com.java.assessment.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Entry Not Found")
public class EntryNotFoundException extends Exception {
    public EntryNotFoundException(String message) {
        super(message);
    }
}
