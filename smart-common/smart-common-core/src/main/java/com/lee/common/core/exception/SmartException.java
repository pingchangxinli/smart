package com.lee.common.core.exception;
/**
 * @Author: lee.li
 * @Date: 2019-08-05
 * @Desc:
 **/
@SuppressWarnings("ALL")
public class SmartException extends Exception {
    /**
     * 错误码
     * */
    private String errCode;
    /**
     * 错误描述
     */
    private String errMsg;

    public SmartException() {
        super();
    }

    public SmartException(String message) {
        super(message);
    }

    public SmartException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmartException(Throwable cause) {
        super(cause);
    }

    public SmartException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
