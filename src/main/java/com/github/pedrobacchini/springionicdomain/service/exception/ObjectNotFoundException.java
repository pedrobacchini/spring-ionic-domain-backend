package com.github.pedrobacchini.springionicdomain.service.exception;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5228691309606396999L;

    public ObjectNotFoundException(String message) { super(message); }

    public ObjectNotFoundException(String message, Throwable cause) { super(message, cause); }
}
