package com.sinsuren.order.management.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by surender.s on 06/10/17.
 */
public class UnprocessableEntityException extends ClientException {

    public UnprocessableEntityException(String message, ResponseEntity response) {
        super(message, response);
    }

    public UnprocessableEntityException(String message, Exception exception) {
        super(message, exception);
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    @Override
    protected HttpStatus getStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
