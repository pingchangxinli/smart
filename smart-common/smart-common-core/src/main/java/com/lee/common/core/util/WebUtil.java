package com.lee.common.core.util;

import org.springframework.web.bind.ServletRequestUtils;

/**
 * web公共类
 * @author lee.li
 */
public class WebUtil {
    /**
     * 根据request  header 中 Authorization param 获取 access token
     *
     * @param authorization request header 中 Authorization 值
     * @return
     */
    public static String getAccessToken(String authorization) {
        return  authorization.substring("Bearer".length()).trim();
    }

    /**
     * 生成authorization
     *
     * @param accessToken 访问token
     * @return authorization 值
     */
    public static String[] createAuthorization(String accessToken) {
        return new String[]{"Bearer " + accessToken};
    }
}
