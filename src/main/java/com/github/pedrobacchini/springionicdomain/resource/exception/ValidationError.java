package com.github.pedrobacchini.springionicdomain.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private static final long serialVersionUID = 3174066705345398821L;

    private List<FieldMessage> erros = new ArrayList<>();

    public ValidationError(Integer status, String message, Long timestamp) {
        super(status, message, timestamp);
    }

    public List<FieldMessage> getErros() {
        return erros;
    }

    public void addError(FieldMessage fieldMessage) {
        this.erros.add(fieldMessage);
    }
}
