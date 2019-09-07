package com.lee.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 不需要验证权限路径,自由访问路径
 *
 * @author haitao.li
 */
@Component
@ConfigurationProperties(prefix = "free.access")
public class FreeAccessPath {
    private static final String[] PATH = new String[]{""};

    private String[] path;

    public String[] getPath() {
        if (path == null || path.length == 0) {
            return PATH;
        }
        List<String> list = new ArrayList();
        for (String url : PATH) {
            list.add(url);
        }
        for (String url : path) {
            list.add(url);
        }
        return list.toArray(new String[list.size()]);
    }
}
