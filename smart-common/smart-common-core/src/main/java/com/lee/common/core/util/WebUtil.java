package com.lee.common.core.util;

import org.springframework.web.bind.ServletRequestUtils;

/**
 * web公共类
 * @author lee.li
 */
public class WebUtil {
    public static String getAccessToken(String authorization) {
        return  authorization.substring("Bearer".length()).trim();
    }
}
