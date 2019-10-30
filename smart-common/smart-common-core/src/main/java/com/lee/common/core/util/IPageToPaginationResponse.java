package com.lee.common.core.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.PaginationResponse;

/**
 * 转换IPage对象 to Pagination
 *
 * @author lee.li
 */
public class IPageToPaginationResponse {
    /**
     * IPage 转换成 Pagination
     *
     * @param iPage 分页信息
     * @param <T>
     * @return
     */
    public static <T> PaginationResponse<T> convertIPageToPagination(IPage iPage) {
        Pagination pagination = new Pagination();
        pagination.setCurrent(iPage.getCurrent());
        pagination.setTotal(iPage.getTotal());
        pagination.setPageSize(iPage.getSize());
        PaginationResponse<T> response = new PaginationResponse<T>(pagination, iPage.getRecords());
        return response;
    }
}
