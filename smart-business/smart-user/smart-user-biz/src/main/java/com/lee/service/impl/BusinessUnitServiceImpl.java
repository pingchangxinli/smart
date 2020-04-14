package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.core.Pagination;
import com.lee.domain.BusinessUnitDO;
import com.lee.domain.BusinessUnitDTO;
import com.lee.enums.EnabledStatusEnum;
import com.lee.exception.BusinessUnitExistedException;
import com.lee.exception.BusinessUnitNotExistedException;
import com.lee.mapper.BusinessUnitMapper;
import com.lee.service.BusinessUnitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haitao Li
 */
@Slf4j
@Service
public class BusinessUnitServiceImpl implements BusinessUnitService {
    private static final String ERROR_NOT_EXIST = "系统中不存在该分部";
    private static final String ERROR_EXISTED = "系统中已存在该分部";
    private static final String ERROR_NEED_ID = "需要ID参数";
    @Resource
    private BusinessUnitMapper mapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public Integer createBusinessUnit(BusinessUnitDTO businessUnitDTO) throws BusinessUnitExistedException {
        BusinessUnitDO businessUnitDO = modelMapper.map(businessUnitDTO, BusinessUnitDO.class);
        return mapper.insert(businessUnitDO);
    }

    @Override
    public BusinessUnitDTO findBusinessUnitById(Long id) {
        BusinessUnitDO businessUnitDO = mapper.selectById(id);
        if (businessUnitDO == null) {
            return null;
        }
        return modelMapper.map(businessUnitDO, BusinessUnitDTO.class);
    }


    @Override
    public IPage<BusinessUnitDTO> pageList(Pagination pagination, BusinessUnitDTO businessUnitDO) {
        Page<BusinessUnitDO> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());

        QueryWrapper queryWrapper = new QueryWrapper();

        String name = businessUnitDO.getName();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        EnabledStatusEnum status = businessUnitDO.getStatus();
        if (status != null) {
            queryWrapper.eq("status", status.getValue());
        }
        IPage<BusinessUnitDO> iPage = mapper.selectPage(page, queryWrapper);
        return convertFromDOPage(iPage);
    }

    @Override
    public Integer updateBusinessUnitById(BusinessUnitDTO businessUnitDTO) throws BusinessUnitNotExistedException {
        Long id = businessUnitDTO.getId();
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(ERROR_NEED_ID);
        }
        BusinessUnitDTO temp = this.findBusinessUnitById(id);
        if (ObjectUtils.isEmpty(temp)) {
            throw new BusinessUnitNotExistedException(ERROR_NOT_EXIST);
        }
        BusinessUnitDO businessUnitDO = modelMapper.map(businessUnitDTO, BusinessUnitDO.class);
        return mapper.updateById(businessUnitDO);
    }

    @Override
    public List<BusinessUnitDTO> findBusinessUnit(BusinessUnitDTO businessUnitDTO) {
        QueryWrapper<BusinessUnitDO> queryWrapper = new QueryWrapper<>();
        if (businessUnitDTO != null) {
            Long id = businessUnitDTO.getId();
            if (id != null && id > 0) {
                queryWrapper.eq("id", id);
            }

            String name = businessUnitDTO.getName();
            if (StringUtils.isNotEmpty(name)) {
                queryWrapper.like("name", name);
            }

            EnabledStatusEnum status = businessUnitDTO.getStatus();

            if (status != null) {
                queryWrapper.eq("status", status.getValue());
            }
        }
        List<BusinessUnitDO> businessUnitDOList = mapper.selectList(queryWrapper);
        List<BusinessUnitDTO> businessUnitDTOList = new ArrayList<>();
        businessUnitDOList.forEach(businessUnitDO1 -> {
            BusinessUnitDTO businessUnitDTO1 = modelMapper.map(businessUnitDO1, BusinessUnitDTO.class);
            businessUnitDTOList.add(businessUnitDTO1);
        });
        return businessUnitDTOList;
    }

    @Override
    public List<BusinessUnitDTO> findBusinessUnitByTenantId(Long tenantId) {
        List<BusinessUnitDTO> businessUnitDTOList = new ArrayList<>();
        LambdaQueryWrapper<BusinessUnitDO> queryWrapper = new QueryWrapper<BusinessUnitDO>().lambda()
                .eq(BusinessUnitDO::getTenantId, tenantId);
        List<BusinessUnitDO> businessUnitDOList = mapper.selectList(queryWrapper);
        businessUnitDOList.forEach(businessUnitDO -> {
            BusinessUnitDTO businessUnitDTO = modelMapper.map(businessUnitDO, BusinessUnitDTO.class);
            businessUnitDTOList.add(businessUnitDTO);
        });
        return businessUnitDTOList;
    }

    /**
     * 将 DO page 转换成 DTO page
     *
     * @param ipage
     * @return
     */
    private IPage<BusinessUnitDTO> convertFromDOPage(IPage<BusinessUnitDO> ipage) {
        long current = ipage.getCurrent();
        long size = ipage.getSize();
        long total = ipage.getTotal();
        List<BusinessUnitDO> records = ipage.getRecords();
        IPage<BusinessUnitDTO> ipage1 = new Page<>(current, size, total);
        List<BusinessUnitDTO> returnRecords = convertFromDO(records);
        ipage1.setRecords(returnRecords);
        return ipage1;
    }

    /**
     * 转换DO TO DTO
     *
     * @param doList
     * @return
     */
    private List<BusinessUnitDTO> convertFromDO(List<BusinessUnitDO> doList) {
        if (CollectionUtils.isEmpty(doList)) {
            return null;
        }
        List<BusinessUnitDTO> list = new ArrayList<>(doList.size());
        doList.forEach(BusinessUnitDO -> {
            BusinessUnitDTO BusinessUnitDTO = modelMapper.map(BusinessUnitDO, BusinessUnitDTO.class);
            if (log.isDebugEnabled()) {
                log.debug("[BusinessUnitService],query dto: {}", BusinessUnitDTO);
            }
            list.add(BusinessUnitDTO);
        });
        return list;
    }
}
