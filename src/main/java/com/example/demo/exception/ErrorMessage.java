package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {

    private Error error;
    private Request request;


}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Error {

    private int statusCode;
    private String status;
    private Instant date;
    private String error;
    private String message;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Request {
    private String url;
    private String method;
}