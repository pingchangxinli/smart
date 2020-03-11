package com.lee.controller;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.common.core.util.WebUtil;
import com.lee.enums.EnabledStatusEnum;
import com.lee.feign.TokenClient;
import com.lee.api.entity.SysUser;
import com.lee.domain.SysUserRequest;
import com.lee.domain.SysUserVO;
import com.lee.enums.UserErrorMessageTipEnum;
import com.lee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author haitao.li
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private TokenClient tokenClient;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Value("${web.password.security.key}")
    private String aseKey;
    @Value("${web.password.security.iv}")
    private String aseIv;

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

    /**
     * 租户下的所有用户
     *
     * @param sysUser
     * @return
     */
    @RequestMapping("/list")
    public BaseResponse findUsers(SysUser sysUser) {
        if (log.isDebugEnabled()) {
            log.debug("[user controller] findUsers,params:" + sysUser);
        }
        List<SysUser> list = userService.findUsers(sysUser);
        return BaseResponse.ok(list);
    }

    /**
     * @param user 注册用户信息
     * @return
     */
    @PostMapping
    public BaseResponse createUser(@RequestBody SysUserRequest user) {
        if (log.isDebugEnabled()) {
            log.debug("[user controller] createUser request params:" + user);
        }
        //状态为 生效状态
        user.setStatus(EnabledStatusEnum.ENABLED);
        String password = this.decode(user.getPassword());
        //密码AES加密
        String passwordEncode = passwordEncoder.encode(password);
        user.setPassword(passwordEncode);
        boolean isSuccess = userService.createUser(user);
        return BaseResponse.ok(1);
    }

    /**
     * 当前用户信息
     *
     * @param authorization 头部授权信息
     * @return 用户信息
     */
    @GetMapping("/currentUser")
    public BaseResponse<SysUser> currentUser(@RequestHeader("authorization") String authorization) {
        BaseResponse<LoginUser> baseResponse = tokenClient.findUserByAccessToken(authorization);
        if (log.isDebugEnabled()) {
            log.debug("UserController, currentUser token,param:{}", baseResponse.getData());
        }
        LoginUser loginUser = baseResponse.getData();
        if (loginUser == null) {
            UserErrorMessageTipEnum tip = UserErrorMessageTipEnum.PARAM_ERROR;
            return BaseResponse.error(tip.getCode(), tip.getMessage());
        }
        Long id = loginUser.getId();
        if (log.isDebugEnabled()) {
            log.debug("[user controller] currentUser loginUser, {}", loginUser);
        }
        SysUser sysUser = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("[User Controller] user id : {}", id);
            }
            sysUser = userService.findUserById(id);
            if (log.isDebugEnabled()) {
                log.debug("[user controller] currentUser, {}", sysUser);
            }
        } catch (Exception e) {
            log.error("[user controller],", e);
        }
        return BaseResponse.ok(sysUser);
    }

    @Deprecated
    @GetMapping("/currentUser1")
    public BaseResponse<LoginUser> currentUser1(@RequestParam("access_token") String accessToken) {
        BaseResponse<LoginUser> baseResponse = tokenClient.findUserByAccessToken(accessToken);
        LoginUser loginUser = baseResponse.getData();
        if (loginUser == null) {
            UserErrorMessageTipEnum tip = UserErrorMessageTipEnum.PARAM_ERROR;
            return BaseResponse.error(tip.getCode(), tip.getMessage());
        }
        Long id = loginUser.getId();
        if (log.isDebugEnabled()) {
            log.debug("[user controller] currentUser loginUser, {}", loginUser);
        }
        return baseResponse;
    }

    /**
     * 分页查询当前租户下的所有用户信息
     *
     * @param pagination 分页
     * @param sysUser    用户
     * @return
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(Pagination pagination, SysUser sysUser) {
        IPage<SysUserVO> iPage = userService.pageList(pagination, sysUser);

        PaginationResponse<SysUserVO> paginationResponse = PaginationResponseUtil.convertIPageToPagination(iPage);

        return BaseResponse.ok(paginationResponse);
    }

    /**
     * 根据用户名得到用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping
    public BaseResponse findUserByUserName(@RequestParam("username") String username) {
        SysUser userDetails = userService.findUserByName(username);
        return BaseResponse.ok(userDetails);
    }

    /**
     * feign调用方法
     *
     * @param username
     * @return
     */
    @GetMapping("/internal/{username}")
    public LoginUser internalFindUserByUsername(@PathVariable("username") String username) {
        LoginUser loginUser = userService.internalFindUserByUserName(username);

        return loginUser;
    }

    @DeleteMapping("/{id}")
    public BaseResponse disabledUserById(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int count = userService.disabledUserById(id);
        return BaseResponse.ok(count);
    }

    @PutMapping
    public BaseResponse updateUserById(@RequestBody SysUserRequest sysUserRequest) {
        HttpStatus status = HttpStatus.OK;
        int count = userService.updateUserById(sysUserRequest);
        return BaseResponse.ok(count);
    }
}
