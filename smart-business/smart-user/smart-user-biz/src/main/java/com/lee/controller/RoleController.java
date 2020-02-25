package com.lee.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.response.BaseResponse;
import com.lee.domain.SysRole;
import com.lee.enums.EnabledStatusEnum;
import com.lee.exception.RoleExistException;
import com.lee.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色
 *
 * @author lee.li
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private SysRoleService roleService;

    /**
     * 创建权限
     * @param role 权限信息
     * @return 增加条数
     */
    @PostMapping
    public BaseResponse createRole(@RequestBody SysRole role) throws RoleExistException {
        Integer count = null;
        role.setEnabled(EnabledStatusEnum.ENABLED);
        count = roleService.createRole(role);
        return BaseResponse.ok(count);
    }

    /**
     * 更新权限
     * @param role 权限
     * @return 更新条数
     */
    @PutMapping("/{id}")
    public BaseResponse updateRoleById(@PathVariable("id") Long id, @RequestBody SysRole role) {
        if (ObjectUtils.isEmpty(role.getId())) {
            role.setId(id);
        }
        Integer count = roleService.updateRoleById(role);
        return BaseResponse.ok(count);
    }

    /**
     * http://127.0.0.1:8300/user-api/role/1?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * 根据ID查询权限信息
     *
     * @param id 权限ID
     * @return 权限信息
     */
    @GetMapping("/{id}")
    public BaseResponse findRoleById(@PathVariable("id") Long id) {
        SysRole role = roleService.findRoleById(id);
        return BaseResponse.ok(role);
    }

    /**
     * http://127.0.0.1:8300/user-api/role/page?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8&page=1&limit=20
     * &name=管理
     * 分页查询权限信息
     *
     * @param name  权限名称
     * @param page  当前页数
     * @param limit 每页条数
     * @return
     */
    @GetMapping(value = "/page", params = {"page", "limit", "name"})
    public BaseResponse findRole(String name, Integer page, Integer limit) {
        IPage<SysRole> sysRoles = roleService.findRolePage(name, page, limit);
        return BaseResponse.ok(sysRoles);
    }

    @GetMapping(value = "/list/ids")
    public BaseResponse findRolesByIds(@RequestParam Long[] ids) {

        List<Long> list = CollectionUtils.arrayToList(ids);
        List<SysRole> roleList = roleService.findRoleByIdList(list);
        return BaseResponse.ok(roleList);
    }

    /**
     * @param userId
     * @return
     */
    @GetMapping("/user_id/{user_id}")
    public BaseResponse findRolesByUserId(@PathVariable("user_id") Long userId) {
        List<SysRole> list = roleService.findRoleByUserId(userId);
        return BaseResponse.ok(list);
    }

    @GetMapping
    public BaseResponse<List> findAllRoles() {
        List<SysRole> list = roleService.findAllRoles();
        return BaseResponse.<List>ok(list);
    }
}
