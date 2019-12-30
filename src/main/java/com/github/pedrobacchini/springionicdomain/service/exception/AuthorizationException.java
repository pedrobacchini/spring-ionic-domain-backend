package com.github.pedrobacchini.springionicdomain.service.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends ApiException {

    private static final long serialVersionUID = -7152205083211696395L;

    public AuthorizationException(String message) { super(message, HttpStatus.FORBIDDEN); }

    public AuthorizationException(String message, Throwable cause) { super(message, cause, HttpStatus.FORBIDDEN); }
}
