package com.example.blogs.exceptions;

public class ErrorResponse {

    private int statusCode;
    private String message;

    public ErrorResponse(String message)
    {
        super();
        this.message = message;
    }

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}