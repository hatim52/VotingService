package com.aconex.tech.exceptions;

public class UnAuthorizedException extends Exception {

    private static final long serialVersionUID = 1L;


    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(Throwable throwable) {
        super(throwable);
    }

    public UnAuthorizedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
