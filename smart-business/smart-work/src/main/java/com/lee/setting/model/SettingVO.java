package com.lee.setting.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author lee.li
 */
@Data
public class SettingVO {
    /**
     * 报表时间
     */
    private LocalDate reportDate;
    /**
     * 门店预估营业额
     */
    private List<SettingBusinessUnitDataVO> businessUnitList;
    /**
     * 伙伴排班类型
     */
    private List<SettingPartnerDataVO> partnerReportList;
}
