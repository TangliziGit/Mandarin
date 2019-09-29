package org.a3.mandarin.back.exception;

public class ApiForbiddenException extends RuntimeException{
    public ApiForbiddenException() {
        super();
    }

    public ApiForbiddenException(String message) {
        super(message);
    }
}
