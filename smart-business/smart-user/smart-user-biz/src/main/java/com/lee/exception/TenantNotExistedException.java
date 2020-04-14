package com.lee.exception;

/**
 * @author haitao Li
 */
public class TenantNotExistedException  extends Exception{
    public TenantNotExistedException() {
        super();
    }
    public TenantNotExistedException(String message) {
        super(message);
    }
    public TenantNotExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
