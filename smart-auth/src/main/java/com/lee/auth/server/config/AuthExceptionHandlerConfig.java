package com.lee.auth.server.config;

import com.lee.common.core.GateWayCode;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Exception处理配置类
 * @author lee.li
 */
@Slf4j
@Configuration
public class AuthExceptionHandlerConfig {
    /**
     * 该类配合SecurityConfig中
     *  http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.error("url: {},exception:{}",request.getRequestURI(),authException);
            GateWayCode responseEnum = GateWayCode.SUCCESS;
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            BaseResponse response1 =
                    BaseResponse.builder().code(responseEnum.getCode()).msg(responseEnum.getMessage())
                            .subCode(String.valueOf(status.value())).subMsg(status.getReasonPhrase()).build();
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtil.toJson(response1));
            response.getWriter().flush();
            response.getWriter().close();

        };
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
}
