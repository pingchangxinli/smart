package com.lee.common.core.exception;

/**
 * @author haitao Li
 */
public class PageException extends Exception {
    public PageException() {
        super();
    }

    public PageException(String message) {
        super(message);
    }

    public PageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageException(Throwable cause) {
        super(cause);
    }

    protected PageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
