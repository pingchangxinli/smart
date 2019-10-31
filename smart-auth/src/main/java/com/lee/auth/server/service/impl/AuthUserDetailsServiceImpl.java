package com.lee.auth.server.service.impl;


import com.lee.auth.server.feign.UserClient;
import com.lee.common.business.domain.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author lee.li
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
