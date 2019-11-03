package com.lee.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lee.common.business.EnabledStatus;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.business.util.IPageToPaginationResponse;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.common.core.util.JsonUtil;
import com.lee.feign.TokenClient;
import com.lee.user.domain.SysUser;
import com.lee.user.domain.SysUserRequest;
import com.lee.user.domain.SysUserResponse;
import com.lee.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    /**
     * 租户下的所有用户
     * @param sysUser
     * @return
     */
    @RequestMapping("/list")
    public BaseResponse findUsers(SysUser sysUser) {
        if (log.isDebugEnabled()) {
            log.debug("[user controller] findUsers,params:" + sysUser);
        }
        List<SysUser> list = userService.findUsers(sysUser);
        return BaseResponse.builder().data(list).build();
    }
    /**
     * http://127.0.0.1:8300/user-api/user?access_token=abfe4ce3-1043-4499-bfab-2671322f04a1
     * {"name":"costcenter1","password":"c112","user_code":"sdfa","cost_center_id":1,"roles":[1,4]}
     * @param user 注册用户信息
     * @return
     */
    @PostMapping
    public BaseResponse createUser(@RequestBody SysUserRequest user) {
        if (log.isDebugEnabled()) {
            log.debug("[user controller] createUser request params:" + user);
        }
        //状态为 生效状态
        user.setStatus(EnabledStatus.ENABLED);
        //密码AES加密
        String passwordEncode = passwordEncode(user.getPassword());
        user.setPassword(passwordEncode);
        boolean isSuccess = userService.createUser(user);
        if(isSuccess) {
            return BaseResponse.builder().build();
        } else {
            return BaseResponse.builder().subCode("999").subMsg("新增用户失败").build();
        }

    }

    /**
     * 加密用户密码
     * @param password 明文
     * @return
     */
    private String passwordEncode(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordEncoder = bCryptPasswordEncoder.encode(password);
        return passwordEncoder;
    }

    /**
     * 当前用户信息
     * @param accessToken token
     * @return 用户信息
     */
    @GetMapping("/currentUser")
    public BaseResponse currentUser(@RequestParam("access_token") String accessToken){
        BaseResponse baseResponse = tokenClient.findUserByAccessToken(accessToken);
        return baseResponse;
    }
    /**
     * 分页查询当前租户下的所有用户信息
     * @param pagination 分页
     * @param sysUser 用户
     * @return
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(Pagination pagination, SysUser sysUser) {
        IPage<SysUserResponse> iPage = userService.pageList(pagination, sysUser);

        PaginationResponse<SysUser> paginationResponse = IPageToPaginationResponse.convertIPageToPagination(iPage);

        BaseResponse response = BaseResponse.builder().data(paginationResponse).build();
        return response;
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

        BaseResponse baseResponse = BaseResponse.builder().data(userDetails).build();
        if (log.isDebugEnabled()) {
            log.debug("find user by code,response is:{}, base Response data class is: {}", userDetails,
                    baseResponse.getData().getClass().getName());

        }
        return baseResponse;
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
        return BaseResponse.builder().data(count).build();
    }

    @PutMapping
    public BaseResponse updateUserById(@RequestBody SysUserRequest sysUserRequest) {
        HttpStatus status = HttpStatus.OK;
        int count = userService.updateUserById(sysUserRequest);
        return BaseResponse.builder().data(count).subCode(String.valueOf(status.value()))
                .subMsg(status.getReasonPhrase()).build();
    }
}
