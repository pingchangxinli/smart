package com.lee.setting.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.setting.mapper.SettingBusinessUnitMapper;
import com.lee.setting.mapper.SettingPartnerMapper;
import com.lee.setting.model.*;
import com.lee.setting.service.SettingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lee.li
 */
@Service
public class SettingServiceImpl implements SettingService {
    @Resource
    private SettingPartnerMapper settingPartnerMapper;
    @Resource
    private SettingBusinessUnitMapper settingBusinessUnitMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mergeSetting(SettingDTO settingDTO) {
        int count = mergeSettingPartner(settingDTO.getPartnerReportList());
        int count1 = mergeSettingBusinessUnit(settingDTO.getBusinessUnitList());
    }

    private int mergeSettingBusinessUnit(List<SettingBusinessUnitDataDTO> businessUnitList) {
        businessUnitList.stream().forEach(settingBusinessUnitDataDTO -> {
            LambdaQueryWrapper<SettingBusinessUnitData> lambdaQueryWrapper = new QueryWrapper<SettingBusinessUnitData>()
                    .lambda().eq(SettingBusinessUnitData::getBusinessUnitId, settingBusinessUnitDataDTO.getBusinessUnitId())
                    .eq(SettingBusinessUnitData::getTenantId, settingBusinessUnitDataDTO.getTenantId())
                    .eq(SettingBusinessUnitData::getReportDate, settingBusinessUnitDataDTO.getReportDate());
            SettingBusinessUnitData settingBusinessUnitDataOld = settingBusinessUnitMapper.selectOne(lambdaQueryWrapper);
            SettingBusinessUnitData settingBusinessUnitDataNew = modelMapper.map(settingBusinessUnitDataDTO, SettingBusinessUnitData.class);
            if (settingBusinessUnitDataOld == null) {
                settingBusinessUnitMapper.insert(settingBusinessUnitDataNew);
            } else {
                settingBusinessUnitMapper.update(settingBusinessUnitDataNew, lambdaQueryWrapper);
            }
        });
        return 1;
    }

    /**
     * 新增或者更新伙伴报表
     *
     * @param partnerReportList 伙伴报表
     * @return
     */
    private int mergeSettingPartner(List<SettingPartnerDataDTO> partnerReportList) {
        partnerReportList.stream().forEach(settingPartnerDataDTO -> {
            LambdaQueryWrapper<SettingPartnerData> lambdaQueryWrapper = new QueryWrapper<SettingPartnerData>().lambda()
                    .eq(SettingPartnerData::getBusinessUnitId, settingPartnerDataDTO.getBusinessUnitId())
                    .eq(SettingPartnerData::getTenantId, settingPartnerDataDTO.getTenantId())
                    .eq(SettingPartnerData::getPartnerId, settingPartnerDataDTO.getPartnerId())
                    .eq(SettingPartnerData::getReportDate, settingPartnerDataDTO.getReportDate());
            SettingPartnerData settingPartnerDataOld = settingPartnerMapper.selectOne(lambdaQueryWrapper);
            SettingPartnerData settingPartnerDataNew = modelMapper.map(settingPartnerDataDTO, SettingPartnerData.class);
            if (settingPartnerDataOld == null) {
                settingPartnerMapper.insert(settingPartnerDataNew);
            } else {
                Wrapper<SettingPartnerData> wrapper = new UpdateWrapper<>();
                settingPartnerMapper.update(settingPartnerDataNew, lambdaQueryWrapper);
            }
        });
        return 1;
    }

    @Override
    public SettingDTO get(SettingDTO entity) {
        return null;
    }

    @Override
    public SettingDTO getById(Object id) {
        return null;
    }

    @Override
    public List<SettingDTO> list(SettingDTO entity) {
        return null;
    }

    @Override
    public IPage<SettingDTO> pageList(Pagination pagination, SettingDTO entity) {
        return null;
    }

    @Override
    public SettingDTO insert(SettingDTO entity) {
        return null;
    }

    @Override
    public Integer delete(SettingDTO entity) {
        return null;
    }

    @Override
    public Integer deleteById(Object id) {
        return null;
    }

    @Override
    public Integer updateById(SettingDTO entity) {
        return null;
    }
}
