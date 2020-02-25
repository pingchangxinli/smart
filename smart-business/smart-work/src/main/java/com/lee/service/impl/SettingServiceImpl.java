package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.enums.BusinessEnum;
import com.lee.common.core.Pagination;
import com.lee.enums.LineTypeEnum;
import com.lee.enums.PeriodEnum;
import com.lee.mapper.BusinessUnitMapper;
import com.lee.mapper.PartnerMapper;
import com.lee.model.*;
import com.lee.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@Service
public class SettingServiceImpl implements SettingService {
    @Resource
    private PartnerMapper partnerMapper;
    @Resource
    private BusinessUnitMapper businessUnitMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mergeSetting(SettingDTO settingDTO) {
        int count = mergeSettingPartner(settingDTO.getPartnerReportList());
        int count1 = mergeSettingBusinessUnit(settingDTO.getBusinessUnitList());
    }

    @Override
    public SettingReportDTO businessUnitReport(Long businessUnitId, PeriodEnum periodType,
                                               LocalDate beginDate, LocalDate endDate) {
        SettingReportDTO settingReportDTO = new SettingReportDTO();
        LambdaQueryWrapper<BusinessUnitData> queryWrapper = new QueryWrapper<BusinessUnitData>().lambda()
                .eq(BusinessUnitData::getBusinessUnitId, businessUnitId)
                .ge(BusinessUnitData::getReportDate, beginDate)
                .le(BusinessUnitData::getReportDate, endDate);
        List<BusinessUnitData> businessUnitDataS = businessUnitMapper.selectList(queryWrapper);
        List<AmountReportDTO> amountReportDTOS = new ArrayList<>();
        List<WorkTimeReportDTO> workTimeReportDTOS = new ArrayList<>();
        List<WorkEfficiencyReportDTO> workEfficiencyReportDTOS = new ArrayList<>();
        if (log.isDebugEnabled()) {
            log.debug("[SettingService],businessUnitDatas:{}", businessUnitDataS);
        }
        businessUnitDataS.stream().forEach(businessUnitData -> {
            LineTypeEnum lineType = businessUnitData.getLineType();
            if (LineTypeEnum.PREDICT_AMOUNT == lineType || LineTypeEnum.REAL_AMOUNT == lineType) {
                AmountReportDTO amountReportDTO = convertBusinessUnitDataToAmountReport(businessUnitData,
                        periodType);
                amountReportDTOS.add(amountReportDTO);
            }
            if (LineTypeEnum.PREDICT_WORK_TIMES == lineType || LineTypeEnum.REAL_WORK_TIMES == lineType) {
                WorkTimeReportDTO workTimeReportDTO = convertBusinessUnitDataToWorkTimeReport(businessUnitData, periodType);
                workTimeReportDTOS.add(workTimeReportDTO);
            }
            if (LineTypeEnum.PREDICT_WORK_EFFICIENCY == lineType || LineTypeEnum.REAL_WORK_EFFICIENCY == lineType) {
                WorkEfficiencyReportDTO workEfficiencyReportDTO =
                        convertBusinessUnitDataToWorkEfficiencyReport(businessUnitData, periodType);
                workEfficiencyReportDTOS.add(workEfficiencyReportDTO);
            }
        });
        settingReportDTO.setBusinessUnitId(businessUnitId);
        settingReportDTO.setBusinessPeriodType(periodType.name());
        settingReportDTO.setBeginDate(beginDate);
        settingReportDTO.setEndDate(endDate);
        settingReportDTO.setAmountList(amountReportDTOS);
        settingReportDTO.setWorkTimeList(workTimeReportDTOS);
        settingReportDTO.setWorkEfficiencyList(workEfficiencyReportDTOS);
        return settingReportDTO;
    }

    /**
     * 分部设置信息转换
     *
     * @param businessUnitData
     * @return
     */
    private WorkEfficiencyReportDTO convertBusinessUnitDataToWorkEfficiencyReport(BusinessUnitData businessUnitData,
                                                                                  PeriodEnum periodType) {

        LineTypeEnum lineType = businessUnitData.getLineType();
        if (LineTypeEnum.PREDICT_WORK_EFFICIENCY.equals(lineType)) {
            WorkEfficiencyReportDTO workEfficiencyReportDTO = new WorkEfficiencyReportDTO();
            workEfficiencyReportDTO.setReportDate(businessUnitData.getReportDate());
            workEfficiencyReportDTO.setType(BusinessEnum.PREDICT.name());
            if (PeriodEnum.BREAKFAST.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getBreakfastAmount());
            }
            if (PeriodEnum.LUNCH.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getLunchAmount());
            }
            if (PeriodEnum.SUPPER_FIRST.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getSupperFirstPhaseAmount());
            }
            if (PeriodEnum.SUPPER_SECOND.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getSupperSecondPhaseAmount());
            }
            if (PeriodEnum.SUPPER_THIRD.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getSupperThirdPhaseAmount());
            }
            return workEfficiencyReportDTO;
        } else if (LineTypeEnum.REAL_WORK_EFFICIENCY.equals(lineType)) {
            WorkEfficiencyReportDTO workEfficiencyReportDTO = new WorkEfficiencyReportDTO();
            workEfficiencyReportDTO.setReportDate(businessUnitData.getReportDate());
            workEfficiencyReportDTO.setType(BusinessEnum.REAL.name());
            if (PeriodEnum.BREAKFAST.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getBreakfastAmount());
            }
            if (PeriodEnum.LUNCH.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getLunchAmount());
            }
            if (PeriodEnum.SUPPER_FIRST.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getSupperFirstPhaseAmount());
            }
            if (PeriodEnum.SUPPER_SECOND.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getSupperSecondPhaseAmount());
            }
            if (PeriodEnum.SUPPER_THIRD.equals(periodType)) {
                workEfficiencyReportDTO.setAmount(businessUnitData.getSupperThirdPhaseAmount());
            }
            return workEfficiencyReportDTO;
        }
        return null;
    }

    /**
     * 分部设置信息转换
     *
     * @param businessUnitData
     * @return
     */
    private WorkTimeReportDTO convertBusinessUnitDataToWorkTimeReport(BusinessUnitData businessUnitData,
                                                                      PeriodEnum periodType) {


        LineTypeEnum lineType = businessUnitData.getLineType();
        if (LineTypeEnum.PREDICT_WORK_TIMES.equals(lineType)) {
            WorkTimeReportDTO workTimeReportDTO = new WorkTimeReportDTO();
            workTimeReportDTO.setReportDate(businessUnitData.getReportDate());
            workTimeReportDTO.setType(BusinessEnum.PREDICT.name());
            if (PeriodEnum.BREAKFAST.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getBreakfastAmount());
            }
            if (PeriodEnum.LUNCH.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getLunchAmount());
            }
            if (PeriodEnum.SUPPER_FIRST.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getSupperFirstPhaseAmount());
            }
            if (PeriodEnum.SUPPER_SECOND.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getSupperSecondPhaseAmount());
            }
            if (PeriodEnum.SUPPER_THIRD.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getSupperThirdPhaseAmount());
            }
            return workTimeReportDTO;
        } else if (LineTypeEnum.REAL_WORK_TIMES.equals(lineType)) {
            WorkTimeReportDTO workTimeReportDTO = new WorkTimeReportDTO();
            workTimeReportDTO.setReportDate(businessUnitData.getReportDate());
            workTimeReportDTO.setType(BusinessEnum.REAL.name());
            if (PeriodEnum.BREAKFAST.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getBreakfastAmount());
            }
            if (PeriodEnum.LUNCH.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getLunchAmount());
            }
            if (PeriodEnum.SUPPER_FIRST.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getSupperFirstPhaseAmount());
            }
            if (PeriodEnum.SUPPER_SECOND.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getSupperSecondPhaseAmount());
            }
            if (PeriodEnum.SUPPER_THIRD.equals(periodType)) {
                workTimeReportDTO.setAmount(businessUnitData.getSupperThirdPhaseAmount());
            }
            return workTimeReportDTO;
        }
        return null;
    }

    /**
     * 分部设置信息转换为图表展示类型
     *
     * @param businessUnitData
     * @return
     */
    private AmountReportDTO convertBusinessUnitDataToAmountReport(BusinessUnitData businessUnitData,
                                                                  PeriodEnum periodType) {
        LineTypeEnum lineType = businessUnitData.getLineType();
        if (LineTypeEnum.PREDICT_AMOUNT.equals(lineType)) {
            AmountReportDTO amountReportDTO = new AmountReportDTO();
            amountReportDTO.setReportDate(businessUnitData.getReportDate());
            amountReportDTO.setType(BusinessEnum.PREDICT.name());
            if (log.isDebugEnabled()) {
                log.debug("[SettingService] name:{},name len:{},type:{},type len:{},isEqual:{},isEqual:{}",
                        PeriodEnum.BREAKFAST.name(), PeriodEnum.BREAKFAST.name().length(),
                        periodType, PeriodEnum.BREAKFAST.name().equals("BREAKFAST"),
                        PeriodEnum.BREAKFAST.name()
                                .equals(periodType));
            }
            if (PeriodEnum.BREAKFAST.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getBreakfastAmount());
            }
            if (PeriodEnum.LUNCH.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getLunchAmount());
            }
            if (PeriodEnum.SUPPER_FIRST.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getSupperFirstPhaseAmount());
            }
            if (PeriodEnum.SUPPER_SECOND.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getSupperSecondPhaseAmount());
            }
            if (PeriodEnum.SUPPER_THIRD.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getSupperThirdPhaseAmount());
            }
            return amountReportDTO;
        }
        if (LineTypeEnum.REAL_AMOUNT == lineType) {
            AmountReportDTO amountReportDTO = new AmountReportDTO();
            amountReportDTO.setReportDate(businessUnitData.getReportDate());
            amountReportDTO.setType(BusinessEnum.REAL.name());
            if (PeriodEnum.BREAKFAST.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getBreakfastAmount());
            }
            if (PeriodEnum.LUNCH.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getLunchAmount());
            }
            if (PeriodEnum.SUPPER_FIRST.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getSupperFirstPhaseAmount());
            }
            if (PeriodEnum.SUPPER_SECOND.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getSupperSecondPhaseAmount());
            }
            if (PeriodEnum.SUPPER_THIRD.equals(periodType)) {
                amountReportDTO.setAmount(businessUnitData.getSupperThirdPhaseAmount());
            }
            return amountReportDTO;
        }
        return null;
    }

    /**
     * 新增或者更新分部设置数据
     *
     * @param businessUnitList
     * @return
     */
    private int mergeSettingBusinessUnit(List<BusinessUnitDataDTO> businessUnitList) {
        if (log.isDebugEnabled()) {
            log.debug("SettingService,mergeSetingBusinessUnit: businessUnitList:{}",
                    businessUnitList);
        }
        businessUnitList.stream().forEach(businessUnitDataDTO -> {
            if (log.isDebugEnabled()) {
                log.debug("SettingService,mergeSetingBusinessUnit: reportDate:{},bu:{}",
                        businessUnitDataDTO.getReportDate(),
                        businessUnitDataDTO.getBusinessUnitId());
            }
            LambdaQueryWrapper<BusinessUnitData> lambdaQueryWrapper = new QueryWrapper<BusinessUnitData>()
                    .lambda().eq(BusinessUnitData::getBusinessUnitId, businessUnitDataDTO.getBusinessUnitId())
                    //.eq(SettingBusinessUnitData::getTenantId, settingBusinessUnitDataDTO.getTenantId())
                    .eq(BusinessUnitData::getReportDate, businessUnitDataDTO.getReportDate())
                    .eq(BusinessUnitData::getLineType, businessUnitDataDTO.getLineType());
            BusinessUnitData businessUnitDataOld = businessUnitMapper.selectOne(lambdaQueryWrapper);
            BusinessUnitData businessUnitDataNew = modelMapper.map(businessUnitDataDTO, BusinessUnitData.class);
            if (log.isDebugEnabled()) {
                log.debug("[SettingService] mergeBusinessUnit DTO:{}", businessUnitDataDTO);
                log.debug("[SettingService] mergeBusinessUnit DO:{}", businessUnitDataNew);
            }
            businessUnitDataNew.setUpdateTime(LocalDateTime.now());
            if (businessUnitDataOld == null) {
                businessUnitDataNew.setCreateTime(LocalDateTime.now());
                businessUnitMapper.insert(businessUnitDataNew);
            } else {
                businessUnitMapper.update(businessUnitDataNew, lambdaQueryWrapper);
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
    private int mergeSettingPartner(List<PartnerDataDTO> partnerReportList) {
        partnerReportList.stream().forEach(settingPartnerDataDTO -> {
            LambdaQueryWrapper<PartnerData> lambdaQueryWrapper = new QueryWrapper<PartnerData>().lambda()
                    .eq(PartnerData::getBusinessUnitId, settingPartnerDataDTO.getBusinessUnitId())
                    //.eq(SettingPartnerData::getTenantId, settingPartnerDataDTO.getTenantId())
                    .eq(PartnerData::getPartnerId, settingPartnerDataDTO.getPartnerId())
                    .eq(PartnerData::getReportDate, settingPartnerDataDTO.getReportDate());
            PartnerData partnerDataOld = partnerMapper.selectOne(lambdaQueryWrapper);
            PartnerData partnerDataNew = modelMapper.map(settingPartnerDataDTO, PartnerData.class);
            partnerDataNew.setUpdateTime(LocalDateTime.now());
            if (partnerDataOld == null) {
                if (log.isDebugEnabled()) {
                    log.debug("SettingService,settingPartner:{}", partnerDataNew);
                }
                partnerDataNew.setCreateDate(LocalDate.now());
                partnerMapper.insert(partnerDataNew);
            } else {
                Wrapper<PartnerData> wrapper = new UpdateWrapper<>();
                partnerMapper.update(partnerDataNew, lambdaQueryWrapper);
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
