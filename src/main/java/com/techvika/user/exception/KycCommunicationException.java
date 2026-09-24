package com.techvika.user.exception;

public class KycCommunicationException extends RuntimeException {
    public KycCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}