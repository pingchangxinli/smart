package com.lee.tenant.exception;

/**
 * @author lee.li
 */
public class CostCenterNotExistedException extends Exception {
    public CostCenterNotExistedException() {
        super();
    }

    public CostCenterNotExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CostCenterNotExistedException(Throwable cause) {
        super(cause);
    }

    protected CostCenterNotExistedException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CostCenterNotExistedException(String errorNotExist) {
        super(errorNotExist);
    }
}
