package com.lee.enums;

/**
 * @author haitao Li
 */
public enum TenantErrorEnum {
    /**
     * 租户不存在
     */
    NOT_EXISTED("TENANT_NOT_EXISTED","租户不存在"),
    /**
     * 租户域名已存在
     */
    EXISTED("TENANT_DOMAIN_EXISTED","租户域名已存在"),
    /**
     * 租户名称错误
     */
    NAME_ERROR("NAME_ERROR","租户名称错误"),
    /**
     * 域名错误
     */
    DOMAIN_ERROR("DOMAIN_ERROR","域名错误"),
    /**
     * 域名参数缺失
     */
    DOMAIN_PARAM_NOT_EXISTED("DOMAIN_PARAM_NOT_EXISTED","域名参数缺失");
    /**
     * 错误代码
     */
    private String errorCode;
    /**
     * 错误描述
     */
    private String errorDes;
    TenantErrorEnum(String errorCode,String errorDes) {
        this.errorCode = errorCode;
        this.errorDes = errorDes;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDes() {
        return errorDes;
    }
}
