package com.lee.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 时段
 *
 * @author lee.li
 */
@Getter
public enum PeriodEnum implements IEnum<Integer> {

    /**
     * A上午低峰段营业
     */
    BREAKFAST(1, "A上午低峰段营业"),
    /**
     * B午市高峰段营业
     */
    LUNCH(2, "B午市高峰段营业"),

    /**
     * C晚市低峰段营业
     */
    SUPPER_FIRST(3, "C晚市低峰段营业"),
    /**
     * D晚市高峰期时段营业
     */
    SUPPER_SECOND(4, "D晚市高峰期时段营业"),
    /**
     * E晚市低峰时段营业
     */
    SUPPER_THIRD(5, "E晚市低峰时段营业");
    private final int value;
    private String description;

    PeriodEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
