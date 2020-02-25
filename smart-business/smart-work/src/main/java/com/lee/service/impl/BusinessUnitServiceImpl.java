package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.mapper.BusinessUnitMapper;
import com.lee.model.BusinessUnitData;
import com.lee.model.BusinessUnitDataDTO;
import com.lee.service.BusinessUnitService;
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
public class BusinessUnitServiceImpl implements BusinessUnitService {
    @Resource
    private BusinessUnitMapper businessUnitMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public List<BusinessUnitDataDTO> getBusinessUnitById(Long businessUnitId, LocalDate reportDate) {
        if (reportDate == null) {
            reportDate = LocalDate.now();
        }
        LambdaQueryWrapper<BusinessUnitData> queryWrapper = new QueryWrapper<BusinessUnitData>().lambda()
                .eq(BusinessUnitData::getBusinessUnitId, businessUnitId)
                .eq(BusinessUnitData::getReportDate, reportDate);
        List<BusinessUnitData> businessUnitDataS = businessUnitMapper.selectList(queryWrapper);
        if (businessUnitDataS == null || businessUnitDataS.size() == 0) {
            return null;
        }
        List<BusinessUnitDataDTO> businessUnitDataDTOS = new ArrayList<>();
        businessUnitDataS.stream().forEach(businessUnitData -> {
            BusinessUnitDataDTO businessUnitDataDTO = modelMapper.map(businessUnitData, BusinessUnitDataDTO.class);
            businessUnitDataDTOS.add(businessUnitDataDTO);
        });
        return businessUnitDataDTOS;
    }
}
