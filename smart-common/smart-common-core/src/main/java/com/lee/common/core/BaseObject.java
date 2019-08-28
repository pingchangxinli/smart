package com.lee.common.core;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haitao.li
 */
@Data
public abstract class BaseObject implements Serializable {
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 操作人
     */
    private String operator;
}
