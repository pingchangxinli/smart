package com.lee.gateway.config;

import com.lee.gateway.handler.KatchaHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import javax.annotation.Resource;

/**
 * @author lee.li
 */
@Slf4j
@Configuration
public class RouteConfig {
    @Resource
    private KatchaHandler katchaHandler;
    @Bean
    public RouterFunction testFunRouterFunction() {
        return RouterFunctions.route(
                RequestPredicates.GET("/vcode")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), katchaHandler);

    }

}
