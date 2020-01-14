package com.lee.tenant.exception;

/**
 * @author lee.li
 */
public class ShopExistedException extends Exception {
    public ShopExistedException() {
        super();
    }

    public ShopExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopExistedException(Throwable cause) {
        super(cause);
    }

    protected ShopExistedException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ShopExistedException(String errorExisted) {
        super(errorExisted);
    }
}
