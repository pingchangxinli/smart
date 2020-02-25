package com.lee.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.core.Contants;
import com.lee.common.core.enums.ResponseStatusEnum;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import com.lee.gateway.AuthIgnored;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 全局过滤器,只需要配置Component,会自动加载
 * 过滤是否请求链接
 * param带入access_token参数 或者 header 带入 Authorization 格式为 Bearer ******
 *
 * @author lee.li
 */
@Slf4j
@Component
public class GlobalAccessFilter implements GlobalFilter, Ordered {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private AuthIgnored authIgnored;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        log.info("[GlobalAccessFilter] url: {},header:{},params:{}", request.getURI(), request.getHeaders(),
                request.getQueryParams());

        //根据域名获取租户信息
        //putTenantIdInRequest(request);

        return chain.filter(exchange);

    }

    /**
     * 提取token
     *
     * @param request 请求
     * @return token
     */
    @Deprecated
    private String extractToken(ServerHttpRequest request) {
        List<String> strings = request.getHeaders().get("Authorization");
        String authToken = null;
        if (strings != null) {
            authToken = strings.get(0).substring(Contants.AUTHORIZATION_PRE.length());
        }

        if (StringUtils.isBlank(authToken)) {
            strings = request.getQueryParams().get("access_token");
            if (strings != null) {
                authToken = strings.get(0);
            }
        }

        return authToken;
    }

    /**
     * 将域名对应的tenant id 放入到redis缓存中,并将tenant id 返回
     *
     * @param request
     * @return
     */
    @Deprecated
    private String putTenantIdInRequest(ServerHttpRequest request) {
        URI uri = request.getURI();
        String host = uri.getHost();
//        BaseResponse response = tenantClient.findTenantByDomain(host);
        BaseResponse response = null;
        if (log.isDebugEnabled()) {
            log.debug("[GlobalAccessFilter putTenantIdInRequest] host:" + host + ",response:" + response + ",data class:" +
                    response.getData().getClass().getName());
        }
        String tenantId = response.getData().toString();
        String[] tenantIdArray = new String[]{tenantId};
        request.mutate().header(Contants.REQUEST_HEADER_TENANT_ID, tenantIdArray);
        return tenantId;
    }

    @Override
    public int getOrder() {
        return -500;
    }
}
