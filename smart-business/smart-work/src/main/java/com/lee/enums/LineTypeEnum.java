package com.lee.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author haitao Li
 * 分部行类型
 */
public enum LineTypeEnum {
    /**
     * 预估金额
     */
    PREDICT_AMOUNT(1, "预估金额"),
    /**
     * 预估工时
     */
    PREDICT_WORK_TIMES(2, "预估工时"),
    /**
     * 预估工效
     */
    PREDICT_WORK_EFFICIENCY(3, "预估工效"),
    /**
     * 实际金额
     */
    REAL_AMOUNT(4, "实际金额"),
    /**
     * 实际工时
     */
    REAL_WORK_TIMES(5, "实际工时"),
    /**
     * 实际工效
     */
    REAL_WORK_EFFICIENCY(6, "实际工效");

    LineTypeEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    @EnumValue
    private final int code;
    private final String descp;

    public int getCode() {
        return code;
    }
}
