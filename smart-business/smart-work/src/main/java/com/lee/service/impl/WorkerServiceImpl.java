package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.common.core.Pagination;
import com.lee.mapper.WorkerMapper;
import com.lee.model.WorkerDO;
import com.lee.model.WorkerDTO;
import com.lee.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@Service
public class WorkerServiceImpl implements WorkerService {
    @Resource
    private WorkerMapper mapper;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public WorkerDTO get(WorkerDTO entity) {
        return null;
    }

    @Override
    public WorkerDTO getById(Object id) {
        WorkerDO workerDO = mapper.selectById((Serializable) id);
        return modelMapper.map(workerDO, WorkerDTO.class);
    }

    @Override
    public List<WorkerDTO> list(WorkerDTO entity) {
        LambdaQueryWrapper<WorkerDO> queryWrapper = selectWhere(entity);
        List<WorkerDO> doList = mapper.selectList(queryWrapper);
        List<WorkerDTO> dtoList = new ArrayList<>();
        doList.stream().forEach(workerDO1 -> {
            WorkerDTO workerDTO = modelMapper.map(workerDO1, WorkerDTO.class);
            dtoList.add(workerDTO);
        });
        return dtoList;
    }

    @Override
    public IPage<WorkerDTO> pageList(Pagination pagination, WorkerDTO workerDTO) {
        Page<WorkerDO> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());

        LambdaQueryWrapper<WorkerDO> lambda = selectWhere(workerDTO);

        IPage<WorkerDO> iPage = mapper.selectPage(page, lambda);
        IPage<WorkerDTO> returnIPage = convertFromDOPage(iPage);
        return returnIPage;
    }

    /**
     * 查询动作 , where条件
     *
     * @param workerDTO
     * @return
     */
    private LambdaQueryWrapper<WorkerDO> selectWhere(WorkerDTO workerDTO) {
        LambdaQueryWrapper<WorkerDO> lambda = new QueryWrapper<WorkerDO>().lambda();
        WorkerDO workerDO = modelMapper.map(workerDTO, WorkerDO.class);
        String name = workerDO.getName();
        Long id = workerDO.getId();
        //伙伴ID
        if (id != null) {
            lambda.eq(WorkerDO::getId, id);
        }
        //伙伴名称
        if (StringUtils.isNotEmpty(name)) {
            lambda.like(WorkerDO::getName, name);
        }
        //分部信息
        if (workerDTO.getBusinessUnitId() != null && workerDTO.getBusinessUnitId() > 0) {
            lambda.eq(WorkerDO::getBusinessUnitId, workerDTO.getBusinessUnitId());
        }
        return lambda;
    }


    @Override
    public WorkerDTO insert(WorkerDTO workerDTO) {
        String name = workerDTO.getName();
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        WorkerDO workerDO = modelMapper.map(workerDTO, WorkerDO.class);
        workerDO.setCreateTime(LocalDateTime.now());
        workerDO.setUpdateTime(LocalDateTime.now());
        int count = mapper.insert(workerDO);
        if (count > 0) {
            return modelMapper.map(workerDO, WorkerDTO.class);
        }
        return null;
    }

    @Override
    public Integer delete(WorkerDTO workerDTO) {
        WorkerDO workerDO = new WorkerDO();
        BeanUtils.copyProperties(workerDTO, workerDO);
        LambdaQueryWrapper<WorkerDO> wrapper = new QueryWrapper<WorkerDO>().lambda();
        Long id = workerDO.getId();
        String name = workerDO.getName();

        if (id > 0) {
            wrapper.eq(WorkerDO::getId, id);
        }
        if (StringUtils.isNotEmpty(name)) {
            wrapper.like(WorkerDO::getName, name);
        }
        return mapper.delete(wrapper);
    }

    @Override
    public Integer deleteById(Object id) {
        WorkerDO workerDO = new WorkerDO();
        workerDO.setId((Long) id);
        return mapper.updateById(workerDO);
    }

    @Override
    public Integer updateById(WorkerDTO workerDTO) {
        Long id = workerDTO.getId();
        if (id == null || id == 0) {
            return 0;
        }
        WorkerDO workerDO = modelMapper.map(workerDTO, WorkerDO.class);
        workerDO.setUpdateTime(LocalDateTime.now());
        int count = mapper.updateById(workerDO);
        return count;
    }

    /**
     * 将 DO page 转换成 DTO page
     *
     * @param ipage
     * @return
     */
    private IPage<WorkerDTO> convertFromDOPage(IPage<WorkerDO> ipage) {
        long current = ipage.getCurrent();
        long size = ipage.getSize();
        long total = ipage.getTotal();
        List<WorkerDO> records = ipage.getRecords();
        IPage<WorkerDTO> ipage1 = new Page<>(current, size, total);
        List<WorkerDTO> returnRecords = convertFromDO(records);
        ipage1.setRecords(returnRecords);
        return ipage1;
    }

    /**
     * 转换DO TO DTO
     *
     * @param doList
     * @return
     */
    private List<WorkerDTO> convertFromDO(List<WorkerDO> doList) {
        if (CollectionUtils.isEmpty(doList)) {
            return null;
        }
        List<WorkerDTO> list = new ArrayList<>(doList.size());
        doList.stream().forEach(workerDO -> {
            WorkerDTO workerDTO = modelMapper.map(workerDO, WorkerDTO.class);
            if (log.isDebugEnabled()) {
                log.debug("[WorkerService],query dto: {}", workerDTO);
            }
            list.add(workerDTO);
        });
        return list;
    }
}
