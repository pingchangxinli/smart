package com.lee.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 不需要验证权限路径,自由访问路径
 *
 * @author haitao Li
 */
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthIgnored {
    private static final String[] PATH = new String[]{"/**/oauth/token"};

    private String[] ignored;

    public String[] getPath() {
        if (ignored == null || ignored.length == 0) {
            return PATH;
        }
        List<String> list = new ArrayList();
        for (String url : PATH) {
            list.add(url);
        }
        for (String url : ignored) {
            list.add(url);
        }
        return list.toArray(new String[list.size()]);
    }
}
