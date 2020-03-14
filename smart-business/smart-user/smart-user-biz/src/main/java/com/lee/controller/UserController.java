package com.lee.controller;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.api.vo.SysUserVO;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.domain.SysUserDO;
import com.lee.domain.SysUserDTO;
import com.lee.enums.EnabledStatusEnum;
import com.lee.feign.TokenClient;
import com.lee.enums.UserErrorMessageTipEnum;
import com.lee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
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
    @Resource
    private ModelMapper modelMapper;

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
     * @param sysUserVO
     * @return
     */
    @RequestMapping("/list")
    public BaseResponse findUsers(SysUserVO sysUserVO) {
        if (log.isDebugEnabled()) {
            log.debug("[user controller] findUsers,params:" + sysUserVO);
        }
        SysUserDTO sysUserDTO = modelMapper.map(sysUserVO, SysUserDTO.class);
        List<SysUserDO> list = userService.findUsers(sysUserDTO);
        return BaseResponse.ok(list);
    }

    /**
     * @param user 注册用户信息
     * @return
     */
    @PostMapping
    public BaseResponse createUser(@RequestBody SysUserVO user) {
        if (log.isDebugEnabled()) {
            log.debug("[user controller] createUser request params:" + user);
        }
        //状态为 生效状态
        user.setStatus(EnabledStatusEnum.ENABLED);
        String password = this.decode(user.getPassword());
        //密码AES加密
        String passwordEncode = passwordEncoder.encode(password);
        user.setPassword(passwordEncode);
        SysUserDTO sysUserDTO = modelMapper.map(user, SysUserDTO.class);
        boolean isSuccess = userService.createUser(sysUserDTO);
        return BaseResponse.ok(1);
    }

    /**
     * 当前用户信息
     *
     * @param authorization 头部授权信息
     * @return 用户信息
     */
    @GetMapping("/currentUser")
    public BaseResponse<SysUserVO> currentUser(@RequestHeader("authorization") String authorization) {
        //授权和认证服务器根据token获取用户简要信息
        BaseResponse<LoginUser> baseResponse = tokenClient.findUserByAccessToken(authorization);
        LoginUser loginUser = baseResponse.getData();
        Long id = loginUser.getId();
        if (loginUser == null || id == null || NumberUtils.compare(id, 0) <= 0) {
            UserErrorMessageTipEnum tip = UserErrorMessageTipEnum.PARAM_ERROR;
            return BaseResponse.error(tip.getCode(), tip.getMessage());
        }
        //根据用户ID得到用户详细信息
        try {
            SysUserDO sysUserDO = userService.findUserById(id);
            if (log.isDebugEnabled()) {
                log.debug("UserController,currentUser:{}", sysUserDO);
            }
            //判断用户状态是否可用
            if (EnabledStatusEnum.DISABLED.equals(sysUserDO.getStatus())) {
                UserErrorMessageTipEnum tip = UserErrorMessageTipEnum.USER_DISABLED;
                return BaseResponse.error(tip.getCode(), tip.getMessage());
            }
            SysUserVO sysUserVO = modelMapper.map(sysUserDO, SysUserVO.class);
            return BaseResponse.ok(sysUserVO);
        } catch (Exception e) {
            log.error("[user controller],", e);
            UserErrorMessageTipEnum tip = UserErrorMessageTipEnum.INTERNAL_SERVER_ERROR;
            return BaseResponse.error(tip.getCode(), tip.getMessage());
        }
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
     * @param sysUserDO  用户
     * @return
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(Pagination pagination, SysUserDO sysUserDO) {
        IPage<SysUserVO> iPage = userService.pageList(pagination, sysUserDO);

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
        SysUserDO userDetails = userService.findUserByName(username);
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
    public BaseResponse updateUserById(@RequestBody SysUserVO sysUserRequest) {
        SysUserDTO sysUserDTO = modelMapper.map(sysUserRequest, SysUserDTO.class);
        int count = userService.updateUserById(sysUserDTO);
        return BaseResponse.ok(count);
    }
}
