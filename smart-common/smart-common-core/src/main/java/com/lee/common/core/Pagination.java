package com.lee.common.core;

import lombok.Data;

/**
 * 分页信息
 *
 * @author haitao Li
 */
@Data
public class Pagination {
    private Long total;
    private Long current = 1L;
    private Long pageSize = 10L;
}
