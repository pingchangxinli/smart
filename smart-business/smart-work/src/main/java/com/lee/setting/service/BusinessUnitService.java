package com.lee.setting.service;

import com.lee.setting.model.BusinessUnitDataDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author lee.li
 * 业务单元设置
 */
public interface BusinessUnitService {
    /**
     * 得到当前业务单元填报数据
     *
     * @param businessUnitId 业务单元ID
     * @param reportDate     报表日期
     * @return
     */
    List<BusinessUnitDataDTO> getBusinessUnitById(Long businessUnitId, LocalDate reportDate);
}
