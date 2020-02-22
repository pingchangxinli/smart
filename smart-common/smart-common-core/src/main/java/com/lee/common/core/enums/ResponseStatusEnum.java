package com.lee.common.core.enums;

/**
 * 网关返回状态
 *
 * @author: lee.li
 **/
public enum ResponseStatusEnum {
    /**
     * 成功
     */
    OK("OK", "成功"),
    /**
     * 失败
     */
    FAILED("FAILED", "失败"),
    /**
     * 交易成功
     */
    SUCCESS("10000", "接口调用成功"),
    /**
     * 未找到当前服务
     */
    SERVICE_NOT_FOUND("20000", "服务不可用"),
    /**
     *权限不足
     */
    AUTH_NOT_ENOUGH("20001","权限不足,请在请求头中设置Authorization"),
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


    ResponseStatusEnum(String code, String message) {
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
