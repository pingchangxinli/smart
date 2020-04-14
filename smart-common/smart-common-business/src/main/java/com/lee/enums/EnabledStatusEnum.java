package com.lee.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 租户状态
 *
 * @author haitao Li
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

    /**
     * 根据value得到枚举类型
     *
     * @param value
     * @return 枚举类
     */
    public static EnabledStatusEnum fromValue(Integer value) {
        for (EnabledStatusEnum type : EnabledStatusEnum.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}