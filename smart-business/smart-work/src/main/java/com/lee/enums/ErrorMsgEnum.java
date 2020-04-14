package com.lee.enums;

/**
 * 错误信息枚举
 *
 * @author haitao Li
 */
public enum ErrorMsgEnum {
    /**
     * 授权不正确
     */
    AUTHORIZATION_ERROR("AUTHORIZATION_ERROR", "缺失授权信息"),
    NO_PARTNER_DATA("NO_PARTNER_DATA", "无伙伴信息"),
    NO_BUSINESS_UNIT_DATA("NOT_FOUND_BUSINESS_UNIT_DATA", "分部无数据"),
    NOT_FIND_BUSINESS_UNIT_BY_USER("NOT_FIND_BUSINESS_UNIT_BY_USER", "用户不属于任何分部");
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
