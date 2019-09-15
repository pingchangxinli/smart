package com.lee.tenant;

/**
 * @author haitao.li
 */
public enum TenantErrorEnum {
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
    DOMAIN_ERROR("DOMAIN_ERROR","域名错误");
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
