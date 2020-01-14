package com.lee.setting.service;

import com.lee.setting.model.SettingBusinessUnitDataDTO;

import java.time.LocalDate;

/**
 * @author lee.li
 * 业务单元设置
 */
public interface SettingBusinessUnitService {
    /**
     * 得到当前业务单元填报数据
     *
     * @param businessUnitId 业务单元ID
     * @param reportDate     报表日期
     * @return
     */
    SettingBusinessUnitDataDTO getBusinessUnitByBusinessUnitId(Long businessUnitId, LocalDate reportDate);
}
