package com.github.pedrobacchini.springionicdomain.service.exception;

import org.springframework.http.HttpStatus;

public class FileException extends ApiException {

    private static final long serialVersionUID = 4846010118610490691L;

    public FileException(String message) { super(message, HttpStatus.BAD_REQUEST); }

    public FileException(String message, Throwable cause) { super(message, cause, HttpStatus.BAD_REQUEST); }
}
