package com.lee.setting.model;

import com.lee.worker.model.WorkerVO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lee.li
 * 门店所有伙伴不同时段排班类型
 */
@Data
public class SettingPartnerDataVO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 门店ID
     */
    private Long businessUnitId;
    /**
     * 伙伴ID
     */
    private Long partnerId;
    /**
     * 伙伴姓名
     */
    private String partnerName;
    /**
     * 排班日期
     */
    private LocalDate reportDate;
    /**
     * 8:00 - 8:30 类型
     */
    private String type0800;
    /**
     *
     */
    private String type0830;
    /**
     *
     */
    private String type0900;
    /**
     *
     */
    private String type0930;
    /**
     *
     */
    /**
     *
     */
    private String type1000;
    /**
     *
     */
    private String type1030;
    /**
     *
     */
    private String type1100;
    /**
     *
     */
    private String type1130;
    /**
     *
     */
    private String type1200;
    /**
     *
     */
    private String type1230;
    /**
     *
     */
    private String type1300;
    /**
     *
     */
    private String type1330;
    /**
     *
     */
    private String type1400;
    /**
     *
     */
    private String type1430;
    /**
     *
     */
    private String type1500;
    /**
     *
     */
    private String type1530;
    /**
     *
     */
    private String type1600;
    /**
     *
     */
    private String type1630;
    /**
     *
     */
    private String type1700;
    /**
     *
     */
    private String type1730;
    /**
     *
     */
    private String type1800;
    /**
     *
     */
    private String type1830;
    /**
     *
     */
    private String type1900;
    /**
     *
     */
    private String type1930;
    /**
     *
     */
    private String type2000;
    /**
     *
     */
    private String type2030;
    /**
     *
     */
    private String type2100;
    /**
     *
     */
    private String type2130;
    /**
     *
     */
    private String type2200;
    /**
     *
     */
    private String type2230;
    /**
     *
     */
    private String type2300;
    /**
     *
     */
    private String type2330;

    /**
     * 创建日期
     */
    private LocalDate createDate;
    /**
     * 更新时刻
     */
    private LocalDateTime updateTime;

}
