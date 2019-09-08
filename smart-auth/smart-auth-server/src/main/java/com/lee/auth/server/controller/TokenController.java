package com.lee.auth.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.core.BaseResponseEnum;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
 * @author haitao.li
 */
@RequestMapping("auth")
public class TokenController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);
    @Resource
    private TokenStore tokenStore;
    @GetMapping("/token/{token}")
    public BaseResponse token(@PathParam("token") String token) {
        OAuth2AccessToken tokenObject = tokenStore.readAccessToken(token);
        try {
            return BaseResponse.builder().data(JsonUtil.toJson(tokenObject)).build();
        } catch (JsonProcessingException e) {
            logger.error("OAuth2AccessToke to JSON error, exception: {}",e);
        }
        return null;
    }
}
