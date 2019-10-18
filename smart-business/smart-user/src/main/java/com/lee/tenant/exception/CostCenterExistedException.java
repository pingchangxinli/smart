package com.lee.tenant.exception;

/**
 * @author lee.li
 */
public class CostCenterExistedException extends Exception {
    public CostCenterExistedException() {
        super();
    }

    public CostCenterExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CostCenterExistedException(Throwable cause) {
        super(cause);
    }

    protected CostCenterExistedException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CostCenterExistedException(String errorExisted) {
        super(errorExisted);
    }
}
