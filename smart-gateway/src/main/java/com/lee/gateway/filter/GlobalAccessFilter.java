package com.lee.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.core.Contants;
import com.lee.common.core.enums.BaseResponseEnum;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import com.lee.gateway.AuthIgnored;
import com.lee.gateway.feign.TenantClient;
import com.lee.gateway.feign.TokenClient;
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
 * @author haitao.li
 */
@Slf4j
@Component
public class GlobalAccessFilter implements GlobalFilter, Ordered {
    @Resource
    private TokenClient tokenClient;
    @Resource
    private TenantClient tenantClient;
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
        putTenantIdInRequest(request);
        for (String s : authIgnored.getPath()) {
            if (pathMatcher.match(s, exchange.getRequest().getPath().value())) {
                return chain.filter(exchange);
            }
        }

        //获取access token
        String accessToken = extractToken(request);
        BaseResponse baseResponse =  tokenClient.token(accessToken);
        log.debug("[GlobalAccessFilter] get user info by access token:{}",baseResponse);

        Map<String,Object> map = (Map<String, Object>) baseResponse.getData();
        boolean isValid = (Integer)map.get("expires_in") > 0 ? true : false;
        log.debug("expires_in is : {} > 0 ? {}", map.get("expires_in"),isValid);
        if (isValid) {
            return chain.filter(exchange);
        } else {
            BaseResponseEnum responseEnum = BaseResponseEnum.AUTH_NOT_ENOUGH;
            BaseResponse response =
                    BaseResponse.builder().code(responseEnum.getCode()).msg(responseEnum.getMessage()).build();
            ServerHttpResponse httpResponse = exchange.getResponse();
            String message = "";
            try {
                message = JsonUtil.toJson(response);
                log.debug("[AccessFilter] token invalid,return message:{}", message);
            } catch (JsonProcessingException e) {
                log.error("[GATEWAY],response no token: {}", e);
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
                //将请求参数中的access_token赋值给请求头Authorization,否则在auth模块会被默认防火墙阻挡
                request.mutate().header("Authorization",new String[]{"Bearer "+authToken});
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
    private String putTenantIdInRequest(ServerHttpRequest request) {
        URI uri = request.getURI();
        String host = uri.getHost();
        BaseResponse response = tenantClient.findTenantByDomain(host);
        String tenantId = (String) response.getData();
        log.debug("get tenant id : {} by domain: {}", response.getData(), host);
        String[] tenantIdArray = new String[]{(String) response.getData()};
        request.mutate().header(Contants.REQUEST_HEADER_TENANT_ID, tenantIdArray);
        return tenantId;
    }

    @Override
    public int getOrder() {
        return -500;
    }
}
