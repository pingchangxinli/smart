package com.lee.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.enums.ErrorMsgEnum;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.model.WorKType;
import com.lee.model.WorKTypeDTO;
import com.lee.model.WorKTypeVO;
import com.lee.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author haitao Li
 * 排班类型
 */
@Slf4j
@RestController
@RequestMapping("/type")
public class TypeController {
    /**
     * 成功
     */
    private static final String SUCCESS = "success";
    /**
     * 失败
     */
    private static final String FAIDED = "failed";
    private static final String NEED_TYPE_PARAM = "缺少类型参数";
    @Resource
    private TypeService typeService;
    private PaginationResponse<WorKType> paginationResponse;
    @Resource
    private ModelMapper modelMapper;

    /**
     * 查询排版类型
     *
     * @param worKType
     * @return
     */
    @GetMapping
    public BaseResponse findType(WorKType worKType) {
        if (log.isDebugEnabled()) {
            log.debug("[TypeController] request params: {}", worKType);
        }
        WorKType worKType1 = typeService.findType(worKType);
        return BaseResponse.ok(worKType1);
    }

    @GetMapping("list")
    public BaseResponse<List<WorKTypeVO>> list(@RequestHeader("authorization") String authorization) {
        if (StringUtils.isEmpty(authorization)) {
            ErrorMsgEnum errorMsgEnum = ErrorMsgEnum.AUTHORIZATION_ERROR;
            return BaseResponse.error(errorMsgEnum.getCode(), errorMsgEnum.getMessage());
        }
        List<WorKTypeVO> voList = new ArrayList<>();
        List<WorKTypeDTO> list = typeService.list();
        list.stream().forEach(worKTypeDTO -> {
            WorKTypeVO vo = modelMapper.map(worKTypeDTO, WorKTypeVO.class);
            voList.add(vo);
        });
        return BaseResponse.ok(voList);
    }

    /**
     * 分页查询排班类型
     *
     * @param pagination
     * @param worKType
     * @return
     */
    @GetMapping("page")
    public BaseResponse pageList(Pagination pagination, WorKType worKType) {
        if (log.isDebugEnabled()) {
            log.debug("[Type controller] page request: pagination:{}, workType: {}", pagination, worKType);
        }
        IPage<WorKType> iPage = typeService.pageList(pagination, worKType);
        if (log.isDebugEnabled()) {
            List<WorKType> list = iPage.getRecords();
            list.stream().forEach(worKType1 -> {
                log.debug("[Type controller] page response worKType1: {}", worKType1);
            });
        }
        PaginationResponse<WorKType> paginationResponse = PaginationResponseUtil.convertIPageToPagination(iPage);
        if (log.isDebugEnabled()) {
            log.debug("[Type controller] page response: {}", paginationResponse.getList());
        }
        return BaseResponse.ok(paginationResponse);
    }

    /**
     * 新增排班类型
     *
     * @param worKType 排班类型
     * @return
     */
    @PostMapping
    public BaseResponse<WorKType> createType(@RequestBody WorKType worKType) {
        if (log.isDebugEnabled()) {
            log.debug("[TypeController createType] request param: {}", worKType);
        }
        WorKType workType1 = typeService.createType(worKType);
        return BaseResponse.<WorKType>ok(workType1);
    }

    @PutMapping
    public BaseResponse updateType(@RequestBody WorKType workType) {
        if (log.isDebugEnabled()) {
            log.debug("[Type controller],request param:{}", workType);
        }
        String type = workType.getType();
        if (StringUtils.isEmpty(type)) {
            return BaseResponse.error(String.valueOf(HttpStatus.BAD_REQUEST.value()), NEED_TYPE_PARAM);
        } else {
            workType.setUpdateTime(LocalDateTime.now());
            int count = typeService.updateTypeById(workType);
            return BaseResponse.<WorKType>ok(workType);
        }
    }
}
