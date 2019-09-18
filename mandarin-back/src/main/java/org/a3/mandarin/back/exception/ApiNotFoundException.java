package org.a3.mandarin.back.exception;

public class ApiNotFoundException extends RuntimeException{
    public ApiNotFoundException() {
        super();
    }

    public ApiNotFoundException(String message) {
        super(message);
    }
}
