package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorMessage handle(HttpServletRequest request, DataIntegrityViolationException ex){
        ErrorMessage message = new ErrorMessage();
        message.setRequest(Request.builder()
                .url(request.getRequestURI())
                .method(request.getMethod())
                .build());

        message.setError(Error.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .date(Instant.now())
                        .error(DataIntegrityViolationException.class.getCanonicalName())
                        .message(ex.getMessage())
                .build());

        return message;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorMessage handle(HttpServletRequest request, IllegalArgumentException ex){
        ErrorMessage message = new ErrorMessage();
        message.setRequest(Request.builder()
                .url(request.getRequestURI())
                .method(request.getMethod())
                .build());

        message.setError(Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .date(Instant.now())
                .error(IllegalArgumentException.class.getCanonicalName())
                .message(ex.getMessage())
                .build());

        return message;
    }
}
