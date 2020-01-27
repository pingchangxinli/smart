package com.lee.setting.service;

import com.lee.common.business.service.BaseService;
import com.lee.enums.PeriodEnum;
import com.lee.setting.model.SettingDTO;
import com.lee.setting.model.SettingReportDTO;

import java.time.LocalDate;

/**
 * @author lee.li
 */

public interface SettingService extends BaseService<SettingDTO> {
    /**
     * 新增或者修改排便报表
     *
     * @param settingDTO
     */
    void mergeSetting(SettingDTO settingDTO);

    /**
     * 分部报表
     *
     * @param businessUnitId
     * @param periodType
     * @param beginDate
     * @param endDate
     * @return
     */
    SettingReportDTO businessUnitReport(Long businessUnitId, PeriodEnum periodType, LocalDate beginDate, LocalDate endDate);
}
