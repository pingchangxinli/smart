package com.lee.common.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author haitao Li
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessObject extends BaseObject{
    /**
     * 租户ID
     */
    private String tenantId;
}
