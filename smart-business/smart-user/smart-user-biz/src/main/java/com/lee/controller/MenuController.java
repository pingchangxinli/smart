package com.lee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.WebUtil;
import com.lee.feign.TokenClient;
import com.lee.util.MenuTreeUtil;
import com.lee.domain.SysMenu;
import com.lee.domain.SysMenuRequest;
import com.lee.domain.SysMenuResponse;
import com.lee.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单处理
 *
 * @author lee.li
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
    private static String ERROR_NO_ROLES = "角色参数缺失";
    @Resource
    private MenuService menuService;
    @Resource
    private TokenClient tokenClient;

    /**
     * 得到用户下的所有菜单
     *
     * @return
     */
    @GetMapping("/current")
    private BaseResponse findMenusByCurrentUser(@RequestHeader("Authorization") String authorization) {

        BaseResponse baseResponse = tokenClient.findUserByAccessToken(authorization);
        if (log.isDebugEnabled()) {
            log.debug("[Menu controller] feign get user result: {}", baseResponse);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUser loginUser = objectMapper.convertValue(baseResponse.getData(), LoginUser.class);

        List<SysMenuResponse> list = menuService.findMenusByUserId(loginUser.getId());

        return BaseResponse.ok(MenuTreeUtil.buildMenuTree(list));
    }

    /**
     * 树状菜单
     *
     * @param sysMenuRequest 菜单请求
     * @return 树状菜单
     */
    @GetMapping("/tree")
    private BaseResponse findMenuTree(SysMenuRequest sysMenuRequest) {
        List<SysMenuResponse> list = menuService.findMenuTree(sysMenuRequest);
        return BaseResponse.builder().data(list).build();
    }

    /**
     * 菜单列表
     *
     * @param sysMenuRequest 菜单请求
     * @return 菜单列表
     */
    @GetMapping("/list")
    private BaseResponse findMenuList(SysMenuRequest sysMenuRequest) {
        List<SysMenu> list = menuService.findMenuList(sysMenuRequest);
        return BaseResponse.builder().data(list).build();
    }

    /**
     * 新增菜单
     *
     * @param sysMenuRequest
     * @return
     */
    @PostMapping
    public BaseResponse createMenu(@RequestBody SysMenuRequest sysMenuRequest) {

        Integer count = menuService.createMenu(sysMenuRequest);

        return BaseResponse.ok(count);
    }

    @PutMapping
    public BaseResponse updateMenu(@RequestBody SysMenuRequest sysMenuRequest) {
        if (CollectionUtils.isEmpty(sysMenuRequest.getRoleIds())) {
            throw new IllegalArgumentException(ERROR_NO_ROLES);
        }
        Integer count = menuService.updateMenu(sysMenuRequest);
        return BaseResponse.ok(count);
    }

    /**
     * 菜单拥有哪些权限
     *
     * @param sysMenuRequest 菜单实体类，id为 menu id，
     * @return 更新记录数
     */
    @PostMapping("/roles")
    public BaseResponse createMenuRoles(@RequestBody SysMenuRequest sysMenuRequest) {
        int count = menuService.createMenuRoles(sysMenuRequest.getId(), sysMenuRequest.getRoleIds());
        return BaseResponse.ok(count);
    }

    /**
     * ID查询出MENU
     *
     * @param id
     * @return
     */
    @GetMapping("/id/{id}")
    public BaseResponse findMenuById(@PathVariable("id") Long id) {
        SysMenu menu = menuService.findMenuById(id);
        return BaseResponse.ok(menu);
    }

    /**
     * 通过角色ID查询出角色下的所有菜单
     *
     * @param roleId 角色ID
     * @return 菜单集合
     */
    @GetMapping("/findMenusByRoleId")
    public BaseResponse findMenusByRoleId(@RequestParam("role_id") Long roleId) {
        List<SysMenu> menus = menuService.findMenusByRoleId(roleId);
        return BaseResponse.ok(menus);
    }

    /**
     * 通过用户Id查询出菜单
     *
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/findMenusByUserId")
    public BaseResponse findMenusByUserId(@RequestParam("user_id") Long userId) {
        List<SysMenuResponse> list = menuService.findMenusByUserId(userId);
        return BaseResponse.ok(list);
    }
}
