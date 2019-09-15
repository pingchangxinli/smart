package com.lee.user.enums;

/**
 * 用户服务响应码
 *
 * @author haitao.li
 */
public enum UserResponseInfo {

    /**
     * 用户已存在
     **/
    USER_EXISTED("USER_EXISTED", "用户已存在"),
    /**
     * 用户不存在
     **/
    USER_NOT_EXISTED("USER_NOT_EXISTED", "用户不存在"),
    /**
     * 密码不正确
     **/
    PASSWORD_ERROR("PASSWORD_ERROR", "密码不正确"),
    /**
     * 用户ID和密码正确
     **/
    SUCCESS("SUCCESS", "用户ID和密码正确"),
    /**
     * 用户状态不可用
     **/
    USER_DISABLED("USER_DISABLED", "用户状态不可用");
    /**
     * 响应码
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;

    UserResponseInfo(String code, String message) {
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
