package com.lee.tenant.exception;

/**
 * @author lee.li
 */
public class ShopNotExistedException extends Exception {
    public ShopNotExistedException() {
        super();
    }

    public ShopNotExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopNotExistedException(Throwable cause) {
        super(cause);
    }

    protected ShopNotExistedException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ShopNotExistedException(String errorNotExist) {
        super(errorNotExist);
    }
}
