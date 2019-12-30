package com.github.pedrobacchini.springionicdomain.service.exception;

import org.springframework.http.HttpStatus;

public class DataIntegrityException extends ApiException {

    private static final long serialVersionUID = -2832411885906090874L;

    public DataIntegrityException(String message) { super(message, HttpStatus.BAD_REQUEST); }

    public DataIntegrityException(String message, Throwable cause) { super(message, cause, HttpStatus.BAD_REQUEST); }
}
