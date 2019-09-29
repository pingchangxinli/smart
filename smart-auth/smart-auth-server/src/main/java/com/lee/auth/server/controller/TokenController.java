package com.lee.auth.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.bussiness.domain.LoginUser;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
 * @author haitao.li
 */
@RestController
@RequestMapping("/token")
public class TokenController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);
    @Resource
    private TokenStore tokenStore;
    @GetMapping
    public BaseResponse token(@RequestParam("access_token") String token) {
        OAuth2AccessToken tokenObject = tokenStore.readAccessToken(token);

            return BaseResponse.builder().data(tokenObject).build();

    }

    /**
     * 通过access token 得到 用户名
     * @param accessToken
     * @return
     */
    @GetMapping("/user")
    public BaseResponse getUserNameByAccessToken(@RequestParam("access_token") String accessToken) {
        OAuth2Authentication auth2Authenitication= tokenStore.readAuthentication(accessToken);

        LoginUser loginUser = (LoginUser) auth2Authenitication.getPrincipal();
        logger.debug("[Token controller],{}",loginUser);
        return BaseResponse.builder().data(loginUser).build();
    }
}
