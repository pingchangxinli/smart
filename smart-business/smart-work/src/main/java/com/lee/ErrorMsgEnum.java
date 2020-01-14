package com.lee;

/**
 * 错误信息枚举
 *
 * @author lee.li
 */
public enum ErrorMsgEnum {
    /**
     * 授权不正确
     */
    AUTHORIZATION_ERROR("AUTHORIZATION_ERROR", "缺失授权信息"),
    NO_PARTNER_DATA("NO_PARTNER_DATA", "无伙伴信息"),
    NO_BUSINESS_UNIT_DATA("NOT_FOUND_BUSINESS_UNIT_DATA", "分部无数据");
    /**
     * 错误代码
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;

    ErrorMsgEnum(String code, String message) {
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
