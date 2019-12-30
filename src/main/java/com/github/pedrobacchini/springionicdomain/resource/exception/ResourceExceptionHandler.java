package com.github.pedrobacchini.springionicdomain.resource.exception;

import com.github.pedrobacchini.springionicdomain.service.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e) {
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
        e.getBindingResult().getFieldErrors().forEach(fieldError -> error.addError(new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage())));
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<StandardError> apiException(ApiException e) {
        StandardError error = new StandardError(e.getStatus().value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

}
