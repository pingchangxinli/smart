package com.lee.auth.server.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.auth.server.feign.UserClient;
import com.lee.common.bussiness.domain.LoginUser;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.MapObjectTransUtil;
import org.slf4j.log;
import org.slf4j.logFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author lee.li
 */

public class AuthUserDetailsServiceImpl extends JdbcUserDetailsManager {
    private static final log log = logFactory.getlog(AuthUserDetailsServiceImpl.class);
    @Resource
    private UserClient userClient;

    public AuthUserDetailsServiceImpl() {
    }

    public AuthUserDetailsServiceImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("feign client info by username: {}", username);
        LoginUser loginUser = userClient.findUserByUserName(username);
        if(log.isDebugEnabled()) {
            log.debug("auth user client result is : {} ",loginUser);
        }
        if (!loginUser.isEnabled()) {
            throw new UsernameNotFoundException("username: "+loginUser.getUsername()+" is disabled.");
        }

        return loginUser;
    }
}
