package com.lee.role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.EnabledStatus;
import com.lee.common.core.response.BaseResponse;
import com.lee.role.domain.SysRole;
import com.lee.role.exception.RoleExistException;
import com.lee.role.service.RoleService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色
 *
 * @author haitao.li
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Resource
    private RoleService roleService;

    /**
     * http://127.0.0.1:8300/user-api/role?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * <p>
     * {"name":"管理员","code":"admin1"}
     * 创建权限
     *
     * @param role 权限信息
     * @return 增加条数
     */
    @PostMapping
    public BaseResponse createRole(@RequestBody SysRole role) throws RoleExistException {
        Integer count = null;
        role.setEnabled(EnabledStatus.ENABLED);
        count = roleService.createRole(role);
        return BaseResponse.builder().data(count).build();
    }

    /**
     * http://127.0.0.1:8300/user-api/role/1?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * <p>
     * {"name":"管理1员","code":"admin2"}
     * 更新权限
     *
     * @param role 权限
     * @return 更新条数
     */
    @PutMapping("/{id}")
    public BaseResponse updateRoleById(@PathVariable("id") Long id, @RequestBody SysRole role) {
        if (ObjectUtils.isEmpty(role.getId())) {
            role.setId(id);
        }
        Integer count = roleService.updateRoleById(role);
        return BaseResponse.builder().data(count).build();
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
        return BaseResponse.builder().data(role).build();
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
        return BaseResponse.builder().data(sysRoles).build();
    }

    @GetMapping(value = "/list/ids")
    public BaseResponse findRolesByIds(@RequestParam Long[] ids) {

        List<Long> list = CollectionUtils.arrayToList(ids);
        List<SysRole> roleList = roleService.findRoleByIdList(list);
        return BaseResponse.builder().data(roleList).build();
    }

    /**
     * http://127.0.0.1:8300/user-api/role/findRolesByUserId?access_token=76d9bea0-7630-4c3c-8807-4093c6b4f053
     * &user_id=1175223732468088833
     *
     * @param userId
     * @return
     */
    @GetMapping("/findRolesByUserId")
    public BaseResponse findRolesByUserId(@RequestParam("user_id") Long userId) {
        List<SysRole> list = roleService.findRoleByUserId(userId);
        return BaseResponse.builder().data(list).build();
    }

}
