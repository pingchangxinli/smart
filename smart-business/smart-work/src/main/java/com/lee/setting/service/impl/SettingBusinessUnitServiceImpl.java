package com.lee.setting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.setting.mapper.SettingBusinessUnitMapper;
import com.lee.setting.model.SettingBusinessUnitData;
import com.lee.setting.model.SettingBusinessUnitDataDTO;
import com.lee.setting.service.SettingBusinessUnitService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author lee.li
 */
@Service
public class SettingBusinessUnitServiceImpl implements SettingBusinessUnitService {
    @Resource
    private SettingBusinessUnitMapper settingBusinessUnitMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public SettingBusinessUnitDataDTO getBusinessUnitByBusinessUnitId(Long businessUnitId, LocalDate reportDate) {
        if (reportDate == null) {
            reportDate = LocalDate.now();
        }
        LambdaQueryWrapper<SettingBusinessUnitData> queryWrapper = new QueryWrapper<SettingBusinessUnitData>().lambda()
                .eq(SettingBusinessUnitData::getBusinessUnitId, businessUnitId)
                .eq(SettingBusinessUnitData::getReportDate, reportDate);
        SettingBusinessUnitData settingBusinessUnitData = settingBusinessUnitMapper.selectOne(queryWrapper);
        if (settingBusinessUnitData == null) {
            return null;
        }
        return modelMapper.map(settingBusinessUnitData, SettingBusinessUnitDataDTO.class);
    }
}
