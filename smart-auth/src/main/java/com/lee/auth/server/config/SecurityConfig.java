package com.lee.auth.server.config;

import com.lee.auth.server.service.impl.AuthUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author haitao Li
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private DataSource dataSource;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 该配置配合oauth2 password模式,根据request提交
     * username 和 password 判断是否存在
     *
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = new AuthUserDetailsServiceImpl(dataSource);
        log.info("[SecurityConfig.userDetailsService] init " + userDetailsService);

        return userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
                .authenticationEventPublisher(new AuthenticationEventPublisher() {
                    @Override
                    public void publishAuthenticationSuccess(Authentication authentication) {
                        log.info("Token request by password grant succeeded.");
                    }

                    @Override
                    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
                        log.info("Token request by password grant failed: {}", exception);
                    }
                })
                .userDetailsService(userDetailsService());
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/oauth/**", "/token/**").permitAll()
                .anyRequest().authenticated();

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }

}
