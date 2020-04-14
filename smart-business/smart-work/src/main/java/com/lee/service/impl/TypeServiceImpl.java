package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.enums.EnabledStatusEnum;
import com.lee.common.core.Pagination;
import com.lee.model.WorKTypeDTO;
import com.lee.mapper.TypeMapper;
import com.lee.model.WorKType;
import com.lee.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haitao Li
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl implements TypeService {
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public WorKType findType(WorKType worKType) {

        QueryWrapper wrapper = new QueryWrapper<WorKType>();
        String type = worKType.getType();
        if (StringUtils.isNotEmpty(type)) {
            //所有数据库中存储的别名都为大写
            type = StringUtils.upperCase(type);
            wrapper.eq("type", type);
        }
        String description = worKType.getDescription();
        if (StringUtils.isNotEmpty(description)) {
            wrapper.like("description", description);
        }
        EnabledStatusEnum status = worKType.getStatus();
        if (status != null) {
            wrapper.eq("status", status.getValue());
        }
        return typeMapper.selectOne(wrapper);
    }

    @Override
    public WorKType createType(WorKType worKType) {
        //新建排班 状态为生效 时间为当前时间
        worKType.setCreateTime(LocalDateTime.now());
        worKType.setUpdateTime(LocalDateTime.now());
        worKType.setStatus(EnabledStatusEnum.ENABLED);
        int count = typeMapper.insert(worKType);
        if (count == 1) {
            return worKType;
        } else {
            return null;
        }
    }

    @Override
    public int updateTypeById(WorKType workType) {
        int count = typeMapper.updateById(workType);
        return count;
    }

    @Override
    public IPage<WorKType> pageList(Pagination pagination, WorKType worKType) {
        Page<WorKType> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());

        QueryWrapper<WorKType> queryWrapper = createQueryWhere(worKType);

        IPage<WorKType> iPage = typeMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public List<WorKTypeDTO> list() {
        List<WorKType> list = typeMapper.selectList(null);
        List<WorKTypeDTO> dtoList = new ArrayList<>();
        list.stream().forEach(worKType -> {
            WorKTypeDTO dto = modelMapper.map(worKType, WorKTypeDTO.class);
            dtoList.add(dto);
        });
        return dtoList;
    }

    /**
     * 根据查询对象创建查询对象
     *
     * @param worKType
     * @return
     */
    private QueryWrapper<WorKType> createQueryWhere(WorKType worKType) {
        QueryWrapper<WorKType> query = new QueryWrapper<>();
        String type = worKType.getType();
        if (StringUtils.isNotEmpty(type)) {
            query.eq("type", type);
        }
        EnabledStatusEnum status = worKType.getStatus();
        if (status != null) {
            query.eq("status", status.getValue());
        }
        String description = worKType.getDescription();
        if (StringUtils.isNotEmpty(description)) {
            query.like("description", description);
        }
        return query;
    }
}
