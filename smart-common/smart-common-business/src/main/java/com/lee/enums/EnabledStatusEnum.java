package com.lee.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 租户状态
 *
 * @author lee.li
 */
public enum EnabledStatusEnum implements IEnum<Integer> {
    /**
     * 启用
     */
    ENABLED(0, "启用"),
    /**
     * 失效
     */
    DISABLED(1, "失效");

    private int value;
    /**
     * 描述
     */
    private String description;

    EnabledStatusEnum(final Integer value, final String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    @JsonValue
    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}