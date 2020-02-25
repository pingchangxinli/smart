package com.lee.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lee.li
 */
@Data
@TableName("work_partner_report")
public class PartnerData {
    /**
     * 租户ID
     */
    @TableId
    private Long tenantId;
    /**
     * 门店ID
     */
    @TableId
    private Long businessUnitId;
    /**
     * 伙伴ID
     */
    @TableId
    private Long partnerId;
    /**
     * 排班日期
     */
    @TableId
    @TableField("report_date")
    private LocalDate reportDate;
    /**
     * 8:00 - 8:30 类型
     */
    @TableField(value = "_0800")
    private String type0800;
    /**
     *
     */
    @TableField(value = "_0830")
    private String type0830;
    /**
     *
     */
    @TableField(value = "_0900")
    private String type0900;
    /**
     *
     */
    @TableField(value = "_0930")
    private String type0930;
    /**
     *
     */
    @TableField(value = "_1000")
    private String type1000;
    /**
     *
     */
    @TableField(value = "_1030")
    private String type1030;
    /**
     *
     */
    @TableField(value = "_1100")
    private String type1100;
    /**
     *
     */
    @TableField(value = "_1130")
    private String type1130;
    /**
     *
     */
    @TableField(value = "_1200")
    private String type1200;
    /**
     *
     */
    @TableField(value = "_1230")
    private String type1230;
    /**
     *
     */
    @TableField(value = "_1300")
    private String type1300;
    /**
     *
     */
    @TableField(value = "_1330")
    private String type1330;
    /**
     *
     */
    @TableField(value = "_1400")
    private String type1400;
    /**
     *
     */
    @TableField(value = "_1430")
    private String type1430;
    /**
     *
     */
    @TableField(value = "_1500")
    private String type1500;
    /**
     *
     */
    @TableField(value = "_1530")
    private String type1530;
    /**
     *
     */
    @TableField(value = "_1600")
    private String type1600;
    /**
     *
     */
    @TableField(value = "_1630")
    private String type1630;
    /**
     *
     */
    @TableField(value = "_1700")
    private String type1700;
    /**
     *
     */
    @TableField(value = "_1730")
    private String type1730;
    /**
     *
     */
    @TableField(value = "_1800")
    private String type1800;
    /**
     *
     */
    @TableField(value = "_1830")
    private String type1830;
    /**
     *
     */
    @TableField(value = "_1900")
    private String type1900;
    /**
     *
     */
    @TableField(value = "_1930")
    private String type1930;
    /**
     *
     */
    @TableField(value = "_2000")
    private String type2000;
    /**
     *
     */
    @TableField(value = "_2030")
    private String type2030;
    /**
     *
     */
    @TableField(value = "_2100")
    private String type2100;
    /**
     *
     */
    @TableField(value = "_2130")
    private String type2130;
    /**
     *
     */
    @TableField(value = "_2200")
    private String type2200;
    /**
     *
     */
    @TableField(value = "_2230")
    private String type2230;
    /**
     *
     */
    @TableField(value = "_2300")
    private String type2300;
    /**
     *
     */
    @TableField(value = "_2330")
    private String type2330;
    /**
     *
     */
    @TableField(value = "_0000")
    private String type0000;
    /**
     *
     */
    @TableField(value = "_0030")
    private String type0030;
    /**
     * 创建日期
     */
    private LocalDate createDate;
    /**
     * 更新时刻
     */
    private LocalDateTime updateTime;
}
