package com.github.pedrobacchini.springionicdomain.resource.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
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

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(AmazonServiceException e) {
        HttpStatus httpStatus = HttpStatus.valueOf(e.getErrorCode());
        StandardError error = new StandardError(httpStatus.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException e) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

}
