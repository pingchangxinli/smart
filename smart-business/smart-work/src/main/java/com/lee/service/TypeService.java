package com.lee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.common.core.Pagination;
import com.lee.model.WorKType;
import com.lee.model.WorKTypeDTO;

import java.util.List;

/**
 * @author haitao Li
 * 排班类型服务类
 */
public interface TypeService {
    /**
     * 查询排班类型
     *
     * @param worKType
     * @return 排班类型
     */
    WorKType findType(WorKType worKType);

    /**
     * 创建排班类型
     *
     * @param worKType
     * @return
     */
    WorKType createType(WorKType worKType);

    /**
     * 更新排班类型
     *
     * @param workType
     * @return
     */
    int updateTypeById(WorKType workType);

    /**
     * 分页查询排班类型
     *
     * @param pagination 分页
     * @param worKType   排班类型
     * @return
     */
    IPage<WorKType> pageList(Pagination pagination, WorKType worKType);

    /**
     * 查询出所有的类型
     *
     * @return
     */
    List<WorKTypeDTO> list();
}
