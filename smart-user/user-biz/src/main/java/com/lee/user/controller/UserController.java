package com.lee.user.controller;

import com.lee.user.UserResponseInfo;
import com.lee.user.domain.User;
import com.lee.user.response.UserResponse;
import com.lee.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author haitao.li
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;


    /**
     * 用户登录
     *
     * @param siteId   站点ID
     * @param userId   用户自定义ID
     * @param password 密码
     * @return 成功与否
     */
    @GetMapping("/signIn")
    public UserResponse signIn(@RequestParam("user_id") String userId, @RequestParam("password") String password) {
        if (log.isDebugEnabled()) {
            log.debug("[USER API] signIn,siteId:{}.userId:{},password:{}", siteId, userId, password);
        }
        User user = userService.loadUserByUsername(userId);
        if (log.isDebugEnabled()) {
            log.debug("[USER API] signIn,user:{}", user);
        }
        UserResponseInfo info;
        if (user == null) {
            info = UserResponseInfo.USER_NOT_EXISTED;
        } else if (!user.isEnabled()) {
            info = UserResponseInfo.USER_DISABLED;
        } else {
            if (!password.equals(user.getPassword())) {
                info = UserResponseInfo.PASSWORD_ERROR;
            } else {
                info = UserResponseInfo.SUCCESS;
            }
        }
        return UserResponse.builder().subCode(info.getCode()).subMsg(info.getMessage()).build();
    }

    /**
     * 用户注册,首先判断用户ID在站点下是否存在
     *
     * @param user 用户信息JSON格式
     * @return 是否成功
     */
    @PostMapping("/signUp")
    public UserResponse createUser(@RequestBody User user) {
        User userTmp = userService.loadUserByUserIdAndSiteId(user.getUserId(), user.getSiteId());
        if (userTmp == null) {
            user.setId(uidGeneratorFeign.getUid());
            userService.createUser(user);
            return UserResponse.builder().build();
        } else {
            UserResponseInfo info = UserResponseInfo.USER_EXISTED;
            return UserResponse.builder().subCode(info.getCode()).subMsg(info.getMessage()).build();
        }

    }
}
