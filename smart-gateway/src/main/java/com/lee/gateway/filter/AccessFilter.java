package com.lee.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.core.BaseResponseEnum;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 全局过滤器,只需要配置Component,会自动加载
 *
 * @author haitao.li
 */
@Component
public class AccessFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("[AccessFilter] url: {},header:{},params:{}", request.getURI(), request.getHeaders(),
                request.getQueryParams());
        //获取access token
        String accessToken = extractToken(request);
        String value = (String) redisTemplate.opsForValue().get("token:" + accessToken);
        if (value != null) {
            return chain.filter(exchange);
        } else {
            BaseResponseEnum responseEnum = BaseResponseEnum.AUTH_NOT_ENOUGH;
            BaseResponse response =
                    BaseResponse.builder().code(responseEnum.getCode()).msg(responseEnum.getMessage()).build();
            ServerHttpResponse httpResponse = exchange.getResponse();
            String message = "";
            try {
                message = JsonUtil.toJson(response);
                logger.debug("[AccessFilter] token invalid,return message:{}" ,message);
            } catch (JsonProcessingException e) {
                logger.error("[GATEWAY],response no token: {}", e);
            }
            byte[] bits = message.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = httpResponse.bufferFactory().wrap(bits);
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            httpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

            return httpResponse.writeWith(Mono.just(buffer));
        }
    }

    /**
     * 提取token
     *
     * @param request 请求
     * @return token
     */
    private String extractToken(ServerHttpRequest request) {
        List<String> strings = request.getHeaders().get("Authorization");
        String authToken = null;
        if (strings != null) {
            authToken = strings.get(0).substring("Bearer".length()).trim();
        }

        if (StringUtils.isBlank(authToken)) {
            strings = request.getQueryParams().get("access_token");
            if (strings != null) {
                authToken = strings.get(0);
            }
        }

        return authToken;
    }

    @Override
    public int getOrder() {
        return -500;
    }
}
