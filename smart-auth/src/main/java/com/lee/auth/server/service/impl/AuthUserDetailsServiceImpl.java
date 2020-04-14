package com.lee.auth.server.service.impl;


import com.lee.auth.server.feign.UserClient;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.exception.SmartException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author haitao Li
 */
@Slf4j
public class AuthUserDetailsServiceImpl extends JdbcUserDetailsManager {
    @Resource
    private UserClient userClient;

    public AuthUserDetailsServiceImpl() {
    }

    public AuthUserDetailsServiceImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        LoginUser loginUser = userClient.findUserByUserName(username);
        //判断用户状态是否正常
        if (!loginUser.isEnabled()) {
            throw new SmartException(username + " is disabled.");
        }
        return loginUser;
    }
}
