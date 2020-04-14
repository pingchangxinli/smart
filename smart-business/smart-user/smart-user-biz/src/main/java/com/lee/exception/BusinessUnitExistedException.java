package com.lee.exception;

/**
 * @author haitao Li
 */
public class BusinessUnitExistedException extends Exception {
    public BusinessUnitExistedException() {
        super();
    }

    public BusinessUnitExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessUnitExistedException(Throwable cause) {
        super(cause);
    }

    protected BusinessUnitExistedException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BusinessUnitExistedException(String errorExisted) {
        super(errorExisted);
    }
}
