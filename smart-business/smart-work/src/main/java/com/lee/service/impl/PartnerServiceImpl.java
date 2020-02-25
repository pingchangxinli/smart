package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.mapper.PartnerMapper;
import com.lee.model.PartnerData;
import com.lee.model.PartnerDataDTO;
import com.lee.service.PartnerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 */
@Service
public class PartnerServiceImpl implements PartnerService {
    @Resource
    private PartnerMapper partnerMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public PartnerDataDTO get(PartnerDataDTO entity) {
        return null;
    }

    @Override
    public PartnerDataDTO getById(Object id) {
        return null;
    }

    @Override
    public List<PartnerDataDTO> list(PartnerDataDTO entity) {
        LambdaQueryWrapper<PartnerData> queryWrapper = new QueryWrapper<PartnerData>().lambda();
        LocalDate reportDate = entity.getReportDate();
        if (reportDate != null) {
            queryWrapper.eq(PartnerData::getReportDate, entity.getReportDate());
        }
        queryWrapper.eq(PartnerData::getBusinessUnitId, entity.getBusinessUnitId());
        List<PartnerData> list = partnerMapper.selectList(queryWrapper);
        List<PartnerDataDTO> partnerDataDTOS = new ArrayList<>();
        list.stream().forEach(partnerData -> {
            PartnerDataDTO partnerDataDTO = modelMapper.map(partnerData, PartnerDataDTO.class);
            partnerDataDTOS.add(partnerDataDTO);
        });
        return partnerDataDTOS;
    }

    @Override
    public IPage<PartnerDataDTO> pageList(Pagination pagination, PartnerDataDTO entity) {
        return null;
    }

    @Override
    public PartnerDataDTO insert(PartnerDataDTO entity) {
        return null;
    }

    @Override
    public Integer delete(PartnerDataDTO entity) {
        return null;
    }

    @Override
    public Integer deleteById(Object id) {
        return null;
    }

    @Override
    public Integer updateById(PartnerDataDTO entity) {
        return null;
    }
}
