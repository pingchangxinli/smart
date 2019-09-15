package com.lee;


import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 租户状态
 *
 * @author haitao.li
 */
@Getter
public enum EnabledStatus implements IEnum<Integer> {
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

    EnabledStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}