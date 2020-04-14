package com.lee.model;

import lombok.Data;

import java.util.List;

/**
 * @author haitao Li
 * 排班设置
 */
@Data
public class SettingDTO {
    /**
     * 门店预估营业额
     */
    private List<BusinessUnitDataDTO> businessUnitList;
    /**
     * 伙伴排班类型
     */
    private List<PartnerDataDTO> partnerReportList;
}
