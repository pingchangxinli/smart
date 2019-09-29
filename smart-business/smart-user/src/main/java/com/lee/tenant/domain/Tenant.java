package com.lee.tenant.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lee.common.business.EnabledStatus;
import com.lee.common.core.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户
 * @author haitao.li
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("sys_tenant")
public class Tenant extends BaseObject {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态
     */
    private EnabledStatus status;
    /**
     * 域名
     */
    private String domain;

}
