package com.lee.common.security.config;

import com.lee.common.core.config.WhiteListProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

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
    @Autowired
    protected RemoteTokenServices remoteTokenServices;
    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info(">>>>>>>>>>hello");
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        log.info("Oauth2ResourceConfig,whiteListProperties urls:{}", whiteListProperties.getUrls());
        List<String> whiteUrls = whiteListProperties.getUrls();
        if (whiteUrls != null && whiteUrls.size() > 0) {
            whiteListProperties.getUrls()
                    .forEach(url -> {
                        log.debug("ResourceConfig,url:", url);
                        registry.antMatchers(url).permitAll();
                    });
        }
        registry.anyRequest().authenticated();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        remoteTokenServices.setRestTemplate(restTemplate);
        resources.tokenServices(remoteTokenServices);
    }

    @Bean
    @Primary
    @LoadBalanced
    public RestTemplate lbRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            @SneakyThrows
            public void handleError(ClientHttpResponse response) {
                if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value()) {
                    super.handleError(response);
                }
            }
        });
        return restTemplate;
    }
//    @Bean
//    @Primary
//    public ResourceServerTokenServices tokenServices() {
//        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
//        remoteTokenServices.setClientId("resource");
//        remoteTokenServices.setClientSecret("resource");
//        return remoteTokenServices;
//    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }
}
