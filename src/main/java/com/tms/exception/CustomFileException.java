package com.tms.exception;

public class CustomFileException extends Exception{
    public CustomFileException(String message) {
        super("File exception: " + message);
    }
}
