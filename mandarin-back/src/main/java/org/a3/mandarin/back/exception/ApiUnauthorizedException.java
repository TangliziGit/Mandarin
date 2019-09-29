package org.a3.mandarin.back.exception;

public class ApiUnauthorizedException extends RuntimeException{
    public ApiUnauthorizedException() {
        super();
    }

    public ApiUnauthorizedException(String message) {
        super(message);
    }
}
