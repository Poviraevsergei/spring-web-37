package com.tms.exception;

public class SecurityNotFound extends RuntimeException{
    public SecurityNotFound() {
        super("Security Not Found");
    }
}
