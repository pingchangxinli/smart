package com.lee.common.core;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lee.li
 */
@Data
public abstract class BaseObject implements Serializable {
    /**
     * 创建时间
     */
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * 更新时间
     */
    private LocalDateTime updateTime = LocalDateTime.now();
    /**
     * 操作人
     */
    private String operator;

}
