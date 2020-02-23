package com.lee.common.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密处理配置
 * @author lee.li
 */
@Slf4j
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("[PasswordEncoderConfig.passwordEncoder] init use BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }
}
