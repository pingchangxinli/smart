package com.lee.common.core;

import lombok.Data;

/**
 * 分页查询
 * @author lee.li
 */
@Data
public class Pagination {
    private Integer total;
    private Integer current;
    private Integer pageSize;
}
