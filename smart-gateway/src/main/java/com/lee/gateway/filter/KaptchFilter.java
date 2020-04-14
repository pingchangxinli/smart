package com.lee.gateway.filter;

import com.lee.gateway.Contants;
import com.lee.gateway.exception.KaptchInvalidException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.security.InvalidParameterException;

/**
 * 验证码判断
 *
 * @author haitao Li
 */
@Slf4j
@Component
@AllArgsConstructor
public class KaptchFilter extends AbstractGatewayFilterFactory {
    private final RedisTemplate redisTemplate;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            //验证码提交参数
            String code = queryParams.getFirst(Contants.PARAM_KAPTCH_KEY);
            // 随机数提交参数
            String random = queryParams.getFirst(Contants.PARAM_RANDOM_KEY);
            try {
                checkKaptch(code, random);
            } catch (KaptchInvalidException e) {
                log.info("[KaptchFilter] path:{},kaptch is not right",path,e);
            }
            return chain.filter(exchange);
        };
    }

    private void checkKaptch(String code, String random) throws KaptchInvalidException {
        if (StringUtils.isEmpty(random)) {
            throw new InvalidParameterException("随机数参数缺失");
        }
        if (StringUtils.isEmpty(code)) {
            throw new InvalidParameterException("验证码缺失");
        }
        String key = Contants.KATCHA_KEY_PRE + random;
        String value = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.equals(code,value)) {
            throw new KaptchInvalidException("验证码不正确");
        }
    }
}
