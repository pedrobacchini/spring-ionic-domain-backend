package com.github.pedrobacchini.springionicdomain.service.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends ApiException {

    private static final long serialVersionUID = 4934003192057535303L;

    public ObjectNotFoundException(String message) { super(message, HttpStatus.NOT_FOUND); }

    public ObjectNotFoundException(String message, Throwable cause) { super(message, cause, HttpStatus.NOT_FOUND); }
}
