package com.lee.setting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.setting.mapper.SettingPartnerMapper;
import com.lee.setting.model.SettingPartnerData;
import com.lee.setting.model.SettingPartnerDataDTO;
import com.lee.setting.service.SettingPartnerService;
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
public class SettingPartnerServiceImpl implements SettingPartnerService {
    @Resource
    private SettingPartnerMapper settingPartnerMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public SettingPartnerDataDTO get(SettingPartnerDataDTO entity) {
        return null;
    }

    @Override
    public SettingPartnerDataDTO getById(Object id) {
        return null;
    }

    @Override
    public List<SettingPartnerDataDTO> list(SettingPartnerDataDTO entity) {
        LambdaQueryWrapper<SettingPartnerData> queryWrapper = new QueryWrapper<SettingPartnerData>().lambda();
        LocalDate reportDate = entity.getReportDate();
        if (reportDate != null) {
            queryWrapper.eq(SettingPartnerData::getReportDate, entity.getReportDate());
        }
        List<SettingPartnerData> list = settingPartnerMapper.selectList(queryWrapper);
        List<SettingPartnerDataDTO> settingPartnerDataDTOS = new ArrayList<>();
        list.stream().forEach(settingPartnerData -> {
            SettingPartnerDataDTO settingPartnerDataDTO = modelMapper.map(settingPartnerData, SettingPartnerDataDTO.class);
            settingPartnerDataDTOS.add(settingPartnerDataDTO);
        });
        return settingPartnerDataDTOS;
    }

    @Override
    public IPage<SettingPartnerDataDTO> pageList(Pagination pagination, SettingPartnerDataDTO entity) {
        return null;
    }

    @Override
    public SettingPartnerDataDTO insert(SettingPartnerDataDTO entity) {
        return null;
    }

    @Override
    public Integer delete(SettingPartnerDataDTO entity) {
        return null;
    }

    @Override
    public Integer deleteById(Object id) {
        return null;
    }

    @Override
    public Integer updateById(SettingPartnerDataDTO entity) {
        return null;
    }
}
