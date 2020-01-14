package com.lee.setting.model;

import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 * 排班设置
 */
@Data
public class SettingDTO {
    /**
     * 门店预估营业额
     */
    private List<SettingBusinessUnitDataDTO> businessUnitList;
    /**
     * 伙伴排班类型
     */
    private List<SettingPartnerDataDTO> partnerReportList;
}
