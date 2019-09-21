package com.lee.common.core;

import lombok.Builder;
import lombok.Data;

/**
 * 分页查询结果
 * @author haitao.li
 */
@Data
@Builder
public class PageResult<T> {
    /** 总数量 **/
    private Long count;
    /**结果集合**/
    private T data;
}
