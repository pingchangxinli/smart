package com.lee.exception;

/**
 * @author haitao Li
 */
public class BusinessUnitNotExistedException extends Exception {
    public BusinessUnitNotExistedException() {
        super();
    }

    public BusinessUnitNotExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessUnitNotExistedException(Throwable cause) {
        super(cause);
    }

    protected BusinessUnitNotExistedException(String message, Throwable cause, boolean enableSuppression,
                                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BusinessUnitNotExistedException(String errorNotExist) {
        super(errorNotExist);
    }
}
