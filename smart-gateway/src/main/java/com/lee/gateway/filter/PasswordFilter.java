package com.lee.gateway.filter;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author haitao Li
 */
@Slf4j
@Component
public class PasswordFilter extends AbstractGatewayFilterFactory {
    @Value("${web.password.security.key}")
    private String aseKey;
    @Value("${web.password.security.iv}")
    private String aseIv;
    private static final String PAPRAM_PASSWORD = "password";

    private String decode(String pass) {
        if (log.isDebugEnabled()) {
            log.debug("pass:{},aseKey:{},aseIv:{}", pass, aseKey, aseIv);
        }
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
                new SecretKeySpec(aseKey.getBytes(), "AES"),
                new IvParameterSpec(aseIv.getBytes()));
        byte[] result = aes.decrypt(HexUtil.decodeHex(pass.toCharArray()));
        return new String(result, StandardCharsets.UTF_8);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            URI uri = exchange.getRequest().getURI();
            exchange.getRequest().getBody();
            String queryParam = uri.getRawQuery();
            Map<String, String> paramMap = HttpUtil.decodeParamMap(queryParam, CharsetUtil.UTF_8);
            String passwordValue = paramMap.get(PAPRAM_PASSWORD);
            //根据是否包含password参数判断 是否需要解密处理
            if (StringUtils.isNotEmpty(passwordValue)) {
                String pd = this.decode(passwordValue);
                paramMap.put(PAPRAM_PASSWORD, pd);
                URI newUri = UriComponentsBuilder.fromUri(uri)
                        .replaceQuery(HttpUtil.toParams(paramMap))
                        .build(true)
                        .toUri();

                ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(newUri).build();
                return chain.filter(exchange.mutate().request(newRequest).build());
            } else {
                return chain.filter(exchange);
            }
        };
    }
}
