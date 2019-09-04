package com.lee.common.core;

/**
 * 网关返回状态
 *
 * @author: haitao.li
 **/
public enum BaseResponseEnum {
    /**
     *交易成功
     */
    SUCCESS("10000","交易成功"),
    /**
     *未找到当前服务
     */
    SERVICE_NOT_FOUND("20000","未找到当前服务"),
    /**
     *权限不足
     */
    AUTH_NOT_ENOUGH("20001","权限不足"),
    /**
     *请求参数缺失
     */
    REQUEST_PARAMETER_MISS("40001","请求参数缺失"),
    /**
     *非法参数
     */
    REQUEST_PARAMETER_INVALID("40002","非法参数");

    private String code;
    private String message;



    BaseResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
