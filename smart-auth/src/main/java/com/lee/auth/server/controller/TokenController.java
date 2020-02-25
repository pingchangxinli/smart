package com.lee.auth.server.controller;

import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lee.li
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
     * @param authorization 授权码
     * @return
     */
    @GetMapping
    public BaseResponse token(@RequestHeader("Authorization") String authorization) {
        String token = WebUtil.getAccessToken(authorization);
        if (log.isDebugEnabled()) {
            log.debug("[TokenController.token] request params:" + authorization);
        }
        OAuth2AccessToken tokenObject = tokenStore.readAccessToken(token);

        return BaseResponse.ok(tokenObject);

    }

    /**
     * 通过access token 得到 用户名
     *
     * @param authorization 授权码
     * @return
     */
    @GetMapping("/user")
    public BaseResponse<LoginUser> getUserNameByAccessToken(@RequestHeader("Authorization") String authorization) {
        String accessToken = WebUtil.getAccessToken(authorization);
        OAuth2Authentication auth2Authenitication = tokenStore.readAuthentication(accessToken);

        LoginUser loginUser = (LoginUser) auth2Authenitication.getPrincipal();
        log.debug("[Token controller],{}", loginUser);
        return BaseResponse.ok(loginUser);
    }
}
