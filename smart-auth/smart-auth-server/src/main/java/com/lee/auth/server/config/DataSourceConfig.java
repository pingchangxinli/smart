package com.lee.auth.server.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.log;
import org.slf4j.logFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lee.li
 */
@Configuration
public class DataSourceConfig {
    private static final log log = logFactory.getlog(DataSourceConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource  dataSource() {
        log.info("[dataSourceConfig] init datasource");
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
