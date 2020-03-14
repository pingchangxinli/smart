package com.lee.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.api.vo.SysRoleVO;
import com.lee.api.vo.SysUserVO;
import com.lee.domain.SysRoleDO;
import com.lee.domain.SysRoleDTO;
import com.lee.domain.SysUserDO;
import com.lee.domain.SysUserDTO;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 */
public class ConvertUtil {
    /**
     * 将DO list 转换成 DTO list
     *
     * @param sysUserDOList 权限DO list
     * @return DTO list
     */
    public static List<SysUserDTO> convertUserListDOToDTO(ModelMapper modelMapper, List<SysUserDO> sysUserDOList) {
        List<SysUserDTO> sysUserDTOList = new ArrayList<>();
        sysUserDOList.forEach(SysUserDO -> {
            SysUserDTO sysUserDTO = modelMapper.map(SysUserDO, SysUserDTO.class);
            sysUserDTOList.add(sysUserDTO);
        });
        return sysUserDTOList;
    }

    /**
     * 将DO list 转换成 DTO list
     *
     * @param sysRoleDOList 权限DO list
     * @return DTO list
     */
    public static List<SysRoleDTO> convertRoleListDOToDTO(ModelMapper modelMapper, List<SysRoleDO> sysRoleDOList) {
        List<SysRoleDTO> sysRoleDTOList = new ArrayList<>();
        sysRoleDOList.forEach(sysRoleDO -> {
            SysRoleDTO sysRoleDTO = modelMapper.map(sysRoleDO, SysRoleDTO.class);
            sysRoleDTOList.add(sysRoleDTO);
        });
        return sysRoleDTOList;
    }

    /**
     * 将DTO list 转换成 VO list
     *
     * @param sysRoleDTOList 权限DTO list
     * @return VO list
     */
    public static List<SysRoleVO> convertRoleListDTOToVO(ModelMapper modelMapper, List<SysRoleDTO> sysRoleDTOList) {
        List<SysRoleVO> sysRoleVOList = new ArrayList<>();
        sysRoleDTOList.forEach(sysRoleDTO -> {
            SysRoleVO sysRoleVO = modelMapper.map(sysRoleDTO, SysRoleVO.class);
            sysRoleVOList.add(sysRoleVO);
        });
        return sysRoleVOList;
    }

    /**
     * 将 DO page 转换成 DTO page
     *
     * @param ipage
     * @return
     */
    public static IPage<SysRoleDTO> convertFromDOPage(ModelMapper modelMapper, IPage<SysRoleDO> ipage) {
        long current = ipage.getCurrent();
        long size = ipage.getSize();
        long total = ipage.getTotal();
        List<SysRoleDO> records = ipage.getRecords();
        IPage<SysRoleDTO> ipage1 = new Page<>(current, size, total);
        List<SysRoleDTO> returnRecords = convertFromDO(modelMapper, records);
        ipage1.setRecords(returnRecords);
        return ipage1;
    }

    /**
     * 转换DO TO DTO
     *
     * @param doList
     * @return
     */
    public static List<SysRoleDTO> convertFromDO(ModelMapper modelMapper, List<SysRoleDO> doList) {
        if (CollectionUtils.isEmpty(doList)) {
            return null;
        }
        List<SysRoleDTO> list = new ArrayList<>(doList.size());
        doList.forEach(SysRoleDO -> {
            SysRoleDTO SysRoleDTO = modelMapper.map(SysRoleDO, SysRoleDTO.class);
            list.add(SysRoleDTO);
        });
        return list;
    }
}
