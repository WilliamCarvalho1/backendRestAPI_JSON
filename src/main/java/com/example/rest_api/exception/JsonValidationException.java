package com.example.rest_api.exception;

public class JsonValidationException extends Exception{
    public JsonValidationException(String error) {
        super(error);
    }
}
