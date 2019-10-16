package com.lee.common.core;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 分页查询结果
 * @author haitao.li
 */
@Data
@Builder
public class PageResult<T> {
    /** 总数量 **/
    private Pagination pagination;
    /**结果集合**/
    private List<T> data;
}
