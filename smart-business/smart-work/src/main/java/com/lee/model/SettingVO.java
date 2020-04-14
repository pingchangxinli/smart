package com.lee.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author haitao Li
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
    private List<BusinessUnitDataVO> businessUnitDataList;
    /**
     * 伙伴排班类型
     */
    private List<PartnerDataVO> partnerReportList;
}
