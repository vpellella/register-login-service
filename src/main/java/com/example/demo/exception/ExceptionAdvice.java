package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handle(HttpServletRequest request, DataIntegrityViolationException ex){
        ErrorMessage message = getErrorMessage(request, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handle(HttpServletRequest request, IllegalArgumentException ex){
        ErrorMessage message = getErrorMessage(request, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handle(HttpServletRequest request, AuthenticationException ex){
        ErrorMessage message = getErrorMessage(request, ex, "Invalid Credentials");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorMessage> handle(HttpServletRequest request, InternalAuthenticationServiceException ex){
        ErrorMessage message = getErrorMessage(request, ex, "Invalid Credentials");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    private ErrorMessage getErrorMessage(HttpServletRequest request, Exception ex){
        return getErrorMessage(request, ex, null);
    }

    private ErrorMessage getErrorMessage(HttpServletRequest request, Exception ex, String customMessage){
        ErrorMessage message = new ErrorMessage();
        String exceptionClassName = ex.getClass().getName();

        message.setRequest(Request.builder()
                .url(request.getRequestURI())
                .method(request.getMethod())
                .build());

        message.setError(Error.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .date(Instant.now())
                .error(exceptionClassName)
                .message(customMessage != null? customMessage: ex.getMessage())
                .build());
        return message;
    }

}
