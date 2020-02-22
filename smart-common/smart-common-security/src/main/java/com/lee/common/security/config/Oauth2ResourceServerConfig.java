package com.lee.common.security.config;

import com.lee.common.core.config.WhiteListProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;

import javax.annotation.Resource;

/**
 * @author lee.li
 * 资源服务器配置
 */
@Slf4j
@Configuration
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    /**
     * 白名单配置
     */
    @Resource
    private WhiteListProperties whiteListProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info(">>>>>>>>>>hello");
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        log.info("Oauth2ResourceConfig,whiteListProperties urls:{}", whiteListProperties.getUrls());
        whiteListProperties.getUrls()
                .forEach(url -> {
                    log.debug("ResourceConfig,url:", url);
                    registry.antMatchers(url).permitAll();
                });
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices());
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {

        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
        return remoteTokenServices;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }
}
