package com.lee.common.business.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.PaginationResponse;

import java.util.List;

/**
 * Pagination工具类
 *
 * @author lee.li
 */
public class PaginationResponseUtil {
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

    public static <T> PaginationResponse<T> buildPagination(long current, long size, long total, List<T> records) {
        Pagination pagination = new Pagination();
        pagination.setCurrent(current);
        pagination.setTotal(total);
        pagination.setPageSize(size);
        PaginationResponse<T> response = new PaginationResponse<T>(pagination, records);
        return response;
    }
}
