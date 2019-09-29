package com.lee.gateway.exception;

/**
 * @author haitao.li
 */
public class KaptchInvalidException extends Exception {
    public KaptchInvalidException() {
        super();
    }

    public KaptchInvalidException(String message) {
        super(message);
    }

    public KaptchInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public KaptchInvalidException(Throwable cause) {
        super(cause);
    }

    protected KaptchInvalidException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
