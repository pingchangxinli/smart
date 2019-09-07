package com.lee.auth.server.config;

import com.lee.auth.server.service.RedisClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author haitao.li
 */
@Configuration
public class OAuth2ServerConfig {
    @Resource
    private DataSource dataSource;
    @Resource
    private RedisTemplate redisTemplate;
    @Bean
    public RedisClientDetailService redisClientDetailService() {
        RedisClientDetailService service = new RedisClientDetailService(dataSource);
        service.setRedisTemplate(redisTemplate);
        return service;
    }
    @EnableAuthorizationServer
    @Configuration
    class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
        @Autowired
        private AuthenticationManager authenticationManager;

        @Resource
        private RedisClientDetailService redisClientDetailService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            //该配置需要正确的数据库, 数据库的报错级别在DEBUG,不是ERROR,报错信息和数据库表无关
            //clients.jdbc(dataSource).clients(clientDetailsService);
            clients.withClientDetails(redisClientDetailService);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authorizationCodeServices(authorizationCodeServices())
                    .authenticationManager(this.authenticationManager).tokenStore(tokenStore())
                    .approvalStoreDisabled();
        }

        @Bean
        protected AuthorizationCodeServices authorizationCodeServices() {
            return new JdbcAuthorizationCodeServices(dataSource);
        }

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }


    }

    @EnableResourceServer
    @Configuration
    class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            super.configure(resources);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/clients/**").authenticated();
        }
    }
}
