package com.lee.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.bussiness.domain.LoginUser;
import com.lee.common.core.response.BaseResponse;
import com.lee.role.domain.SysRole;
import com.lee.user.domain.SysUser;
import com.lee.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
     * http://127.0.0.1:8300/user-api/user?access_token=abfe4ce3-1043-4499-bfab-2671322f04a1
     * {"name":"costcenter1","password":"c112","user_code":"sdfa","cost_center_id":1,"roles":[1,4]}
     * @param user 注册用户信息
     * @return
     */
    @PostMapping
    public BaseResponse createUser(@RequestBody SysUser user) {
        boolean isSuccess = userService.createUser(user);
        if(isSuccess) {
            return BaseResponse.builder().build();
        } else {
            return BaseResponse.builder().subCode("999").subMsg("新增用户失败").build();
        }

    }

    /**
     * 分页查询当前租户下的所有用户信息
     * @param current 当前页面
     * @param limit 每页总条数
     * @param userCode 用户自定义code 模糊查询
     * @param username 用户名 模糊查询
     * @return
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(@RequestParam("page") Integer current,@RequestParam("limit") Integer limit,
                                 @RequestParam(value = "user_code",required = false) String userCode,
                                 @RequestParam(value = "user_name",required = false) String username) {
        IPage<SysUser> iPage = userService.pageList(current,limit,userCode,username);
        if (logger.isDebugEnabled()) {
            logger.debug("query user page, result is : {}",iPage);
        }
        return BaseResponse.builder().data(iPage).build();
    }

    /**
     * 根据用户名得到用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping
    public BaseResponse findUserByUserCode(@RequestParam("username") String username) {
        SysUser userDetails = userService.findUserByName(username);

        BaseResponse baseResponse = BaseResponse.builder().data(userDetails).build();
        if (logger.isDebugEnabled()) {
            logger.debug("find user by code,response is:{}, base Response data class is: {}", userDetails,
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
        int count = userService.disabledUserById(id);
        return BaseResponse.builder().data(count).build();
    }
}
