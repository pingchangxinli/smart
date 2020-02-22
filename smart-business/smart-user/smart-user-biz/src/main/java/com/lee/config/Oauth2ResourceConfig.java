//package com.lee.config;
//
//import com.lee.common.core.config.WhiteListProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//
//import javax.annotation.Resource;
//
///**
// * @author lee.li
// * 资源服务器配置
// */
//@Slf4j
//@Configuration
//public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        log.info(">>>>>>>>>>hello");
//        http.authorizeRequests().antMatchers("/user/*").permitAll();
//
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenServices(tokenServices());
//    }
//
//    @Bean
//    public ResourceServerTokenServices tokenServices() {
//
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
//        return remoteTokenServices;
//    }
//
//    @Bean
//    public AccessTokenConverter accessTokenConverter() {
//        return new DefaultAccessTokenConverter();
//    }
//}
