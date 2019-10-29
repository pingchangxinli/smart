package com.lee.auth.server.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lee.li
 */
@Slf4j
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource  dataSource() {
        log.info("[DataSourceConfig.dataSource] init datasource");
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
