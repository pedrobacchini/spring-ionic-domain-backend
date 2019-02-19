package com.github.pedrobacchini.springionicdomain.service.exception;

public class DataIntegrityException extends RuntimeException {

    private static final long serialVersionUID = -7732731356949440658L;

    public DataIntegrityException(String message) { super(message); }

    public DataIntegrityException(String message, Throwable cause) { super(message, cause); }
}
