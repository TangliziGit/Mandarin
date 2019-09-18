package org.a3.mandarin.common.exception;

public class PayException extends RuntimeException{
    public PayException() {
        super();
    }

    public PayException(String message) {
        super(message);
    }
}
