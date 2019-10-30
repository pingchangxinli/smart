package com.lee.common.core;

import lombok.Data;

/**
 * 分页信息
 * @author lee.li
 */
@Data
public class Pagination {
    private Long total;
    private Long current;
    private Long pageSize;
}
