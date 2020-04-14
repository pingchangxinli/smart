package com.lee.common.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;

import java.util.List;

/**
 * @author haitao Li
 */
public interface BaseService<T> {
    /**
     * 查询单条记录
     *
     * @param entity
     * @return
     */
    T get(T entity);

    /**
     * 根据主键查询出单条记录
     *
     * @param id
     * @return
     */
    T getById(Object id);

    /**
     * 查询列表
     *
     * @param entity
     * @return
     */
    List<T> list(T entity);

    /**
     * 分页查询
     *
     * @param pagination
     * @param entity
     * @return
     */
    IPage<T> pageList(Pagination pagination, T entity);

    /**
     * 添加
     *
     * @param entity
     */
    T insert(T entity);

    /**
     * 删除
     *
     * @param entity
     */
    Integer delete(T entity);

    /**
     * 根据Id删除
     *
     * @param id
     */
    Integer deleteById(Object id);

    /**
     * 根据id更新
     *
     * @param entity
     */
    Integer updateById(T entity);
}
