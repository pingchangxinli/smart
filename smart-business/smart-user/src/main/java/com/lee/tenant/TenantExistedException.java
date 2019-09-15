package com.lee.tenant;

/**
 * @author haitao.li
 */
public class TenantExistedException extends Exception{
    public TenantExistedException() {
        super();
    }
    public TenantExistedException(String message) {
        super(message);
    }
    public TenantExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}