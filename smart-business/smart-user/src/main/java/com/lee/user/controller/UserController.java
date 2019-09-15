package com.lee.user.controller;

import com.lee.common.bussiness.domain.LoginUser;
import com.lee.common.core.response.BaseResponse;
import com.lee.user.domain.SysUser;
import com.lee.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author haitao.li
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    /**
     * 根据用户名得到用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/{username}")
    public BaseResponse findUserByUserCode(@PathVariable("username") String username) {
        SysUser userDetails = userService.findUserByName(username);

        BaseResponse baseResponse = BaseResponse.builder().data(userDetails).build();
        if (logger.isDebugEnabled()) {
            logger.debug("find user by code,response is:{}, base Response data class is: {}", userDetails,
                    baseResponse.getData().getClass().getName());

        }
        return baseResponse;
    }

    @GetMapping("/internal/{username}")
    public LoginUser internalFindUserByUsername(@PathVariable("username") String username) {
        LoginUser loginUser = userService.internalFindUserByUserName(username);
        return loginUser;
    }
}
