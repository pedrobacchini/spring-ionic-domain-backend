package com.github.pedrobacchini.springionicdomain.resource.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {

    private static final long serialVersionUID = 3174066705345398821L;

    private List<FieldMessage> erros = new ArrayList<>();

    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addError(FieldMessage fieldMessage) { this.erros.add(fieldMessage); }
}
