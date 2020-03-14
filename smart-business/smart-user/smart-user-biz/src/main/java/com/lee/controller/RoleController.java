package com.lee.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.domain.SysRoleDO;
import com.lee.domain.SysRoleDTO;
import com.lee.api.vo.SysRoleVO;
import com.lee.enums.EnabledStatusEnum;
import com.lee.exception.RoleExistException;
import com.lee.service.SysRoleService;
import com.lee.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    @Resource
    private ModelMapper modelMapper;

    /**
     * 创建权限
     *
     * @param role 权限信息
     * @return 增加条数
     */
    @PostMapping
    public BaseResponse createRole(@RequestBody SysRoleDTO role) throws RoleExistException {
        SysRoleDTO sysRoleDTO = modelMapper.map(role, SysRoleDTO.class);
        Integer count = null;
        count = roleService.createRole(sysRoleDTO);
        return BaseResponse.ok(count);
    }

    /**
     * 更新权限
     *
     * @param sysRoleVO 权限
     * @return 更新条数
     */
    @PutMapping
    public BaseResponse updateRoleById(@RequestBody SysRoleVO sysRoleVO) {
        if (log.isDebugEnabled()) {
            log.debug("updateRoleById,param:{}", sysRoleVO);
        }
        SysRoleDTO sysRoleDTO = modelMapper.map(sysRoleVO, SysRoleDTO.class);
        Integer count = roleService.updateRoleById(sysRoleDTO);
        return BaseResponse.ok(count);
    }

    /**
     * 根据ID查询权限信息
     *
     * @param id 权限ID
     * @return 权限信息
     */
    @GetMapping("/{id}")
    public BaseResponse findRoleById(@PathVariable("id") Long id) {
        SysRoleDTO role = roleService.findRoleById(id);
        SysRoleVO sysRoleVO = modelMapper.map(role, SysRoleVO.class);
        return BaseResponse.ok(sysRoleVO);
    }

    /**
     * http://127.0.0.1:8300/user-api/role/page?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8&page=1&limit=20
     * &name=管理
     * 分页查询权限信息
     *
     * @return
     */
    @GetMapping(value = "/page")
    public BaseResponse findRole(Pagination pagination, SysRoleVO sysRoleVO) {
        SysRoleDTO sysRoleDTO = modelMapper.map(sysRoleVO, SysRoleDTO.class);
        IPage<SysRoleDTO> iPage = roleService.findRolePage(pagination, sysRoleDTO);
        PaginationResponse response = PaginationResponseUtil.convertIPageToPagination(iPage);
        return BaseResponse.ok(response);
    }

    @GetMapping(value = "/list/ids")
    public BaseResponse findRolesByIds(@RequestParam Long[] ids) {

        List<Long> list = CollectionUtils.arrayToList(ids);
        List<SysRoleDTO> roleList = roleService.findRoleByIdList(list);
        List<SysRoleVO> roleVOList = ConvertUtil.convertRoleListDTOToVO(modelMapper, roleList);
        return BaseResponse.ok(roleVOList);
    }

    /**
     * @param userId
     * @return
     */
    @GetMapping("/user_id/{user_id}")
    public BaseResponse findRolesByUserId(@PathVariable("user_id") Long userId) {
        List<SysRoleDTO> list = roleService.findRoleByUserId(userId);
        return BaseResponse.ok(list);
    }

    @GetMapping
    public BaseResponse<List> findRolesByStatus(@RequestParam("status") Integer status) {
        EnabledStatusEnum statusEnum = EnabledStatusEnum.fromValue(status);
        List<SysRoleDTO> list = roleService.findRolesByStatus(statusEnum);
        List<SysRoleVO> roleVOList = ConvertUtil.convertRoleListDTOToVO(modelMapper, list);
        return BaseResponse.ok(roleVOList);
    }
}
