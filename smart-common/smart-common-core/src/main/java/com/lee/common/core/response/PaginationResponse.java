package com.lee.common.core.response;

import com.lee.common.core.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 分页查询结果
 *
 * @author haitao Li
 */
@Data
@AllArgsConstructor
@Builder
public class PaginationResponse<T> {
    /** 总数量 **/
    private Pagination pagination;
    /**结果集合**/
    private List<T> list;
}
