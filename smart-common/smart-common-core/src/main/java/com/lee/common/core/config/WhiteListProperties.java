package com.lee.common.core.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haitao Li
 */
@Data
@Configuration
//@ConditionalOnExpression("!'${white-list}'.isEmpty()")
@ConfigurationProperties(prefix = "white-list")
public class WhiteListProperties {
    /**
     * 放行的API白名单
     */
    private List<String> urls = new ArrayList();
}
