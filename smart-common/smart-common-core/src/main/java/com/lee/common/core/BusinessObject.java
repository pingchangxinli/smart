package com.lee.common.core;

import lombok.Data;

/**
 * @author haitao.li
 */
@Data
public class BusinessObject extends BaseObject{
    /**
     * 租户ID
     */
    private String tenantId;
}
