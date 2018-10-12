package com.aconex.tech.exceptions;

public class MissingResourceException extends Exception {

    private static final long serialVersionUID = 1L;

    public MissingResourceException(String message) {
        super(message);
    }

    public MissingResourceException(Throwable throwable) {
        super(throwable);
    }

    public MissingResourceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
