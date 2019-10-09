package com.lee.auth.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.bussiness.domain.LoginUser;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

/**
 * @author haitao.li
 */
@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private TokenStore tokenStore;

    /**
     *
     * @param token
     * @return
     */
    @GetMapping
    public BaseResponse token(@RequestParam("access_token") String token) {
        log.debug("Token controller,request.getRequestURI()：{},request.getContextPath():{},request.getServletPath():{}," +
                        "request.getPathInfo():{}"
                ,request.getRequestURI(),request.getContextPath(),request.getServletPath(),request.getPathInfo());
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
        log.debug("[Token controller],{}",loginUser);
        return BaseResponse.builder().data(loginUser).build();
    }
}
