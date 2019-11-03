package com.lee.menu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.business.util.IPageToPaginationResponse;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.common.core.util.WebUtil;
import com.lee.feign.TokenClient;
import com.lee.menu.MenuTreeUtil;
import com.lee.menu.domain.Menu;
import com.lee.menu.domain.SysMenu;
import com.lee.menu.service.MenuService;
import com.lee.role.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private MenuService menuService;
    @Resource
    private TokenClient tokenClient;
    @Resource
    private SysRoleService roleService;
    @Resource
    private HttpServletRequest request;

    /**
     * http://127.0.0.1:8300/user-api/menu/current?access_token=8667c329-4011-436b-a6dd-26177368379f
     * 得到用户下的所有菜单
     * @return
     */
    @GetMapping("/current")
    private BaseResponse findMenusByCurrentUser(@RequestHeader("Authorization") String authorization) {
        String accessToken = WebUtil.getAccessToken(authorization);
        BaseResponse baseResponse = tokenClient.findUserByAccessToken(accessToken);
        if (log.isDebugEnabled()) {
            log.debug("[Menu controller] feign get user result: {}", baseResponse);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LoginUser loginUser = objectMapper.convertValue(baseResponse.getData(), LoginUser.class);

        List<SysMenu> list = menuService.findMenusByUserId(loginUser.getId());
        if (log.isDebugEnabled()) {
            log.debug("[Menu controller],param:{},response:{}", accessToken,list);
        }
        return BaseResponse.builder().data(MenuTreeUtil.buildMenuTree(list)).build();
    }

    /**
     * 查询菜单
     * @param menu
     * @return
     */
    @GetMapping
    private BaseResponse findMenus(SysMenu menu) {
        List<SysMenu> list = menuService.findAllMenus(menu);
        return BaseResponse.builder().data(MenuTreeUtil.buildMenuTree(list)).build();
    }
    /**
     * http://127.0.0.1:8300/admin-api/menu?access_token=76d9bea0-7630-4c3c-8807-4093c6b4f053
     * {"name":"first url","parent_id":0,"path":"http://www.baidu.com","sort":1,"tenant_id":2}
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    @PostMapping
    public BaseResponse createMenu(@RequestBody SysMenu sysMenu) {
        Integer count = menuService.createMenu(sysMenu);

        return BaseResponse.builder().data(count).build();
    }

    /**
     * http://127.0.0.1:8300/user-api/menu/roles?access_token=76d9bea0-7630-4c3c-8807-4093c6b4f053
     * {
     * "id":"1",
     * "roleIds": [1,2]
     * }
     * 菜单拥有哪些权限
     *
     * @param sysMenu 菜单实体类，id为 menu id，
     * @return 更新记录数
     */
    @PostMapping("/roles")
    public BaseResponse createMenuRoles(@RequestBody SysMenu sysMenu) {
        int count = menuService.createMenuRoles(sysMenu.getId(), sysMenu.getRoleIds());
        return BaseResponse.builder().data(count).build();
    }

    /**
     * ID查询出MENU
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public BaseResponse findMenuById(@PathVariable("id") Long id) {
        SysMenu menu = menuService.findMenuById(id);
        return BaseResponse.builder().data(menu).build();
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
        return BaseResponse.builder().data(menus).build();
    }

    /**
     * 通过用户Id查询出菜单
     *
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/findMenusByUserId")
    public BaseResponse findMenusByUserId(@RequestParam("user_id") Long userId) {
        List<SysMenu> list = menuService.findMenusByUserId(userId);
        return BaseResponse.builder().data(list).build();
    }
}
