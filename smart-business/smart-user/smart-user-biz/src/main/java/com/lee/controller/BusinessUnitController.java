package com.lee.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.api.vo.BusinessUnitVO;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.domain.BusinessUnitDTO;
import com.lee.enums.EnabledStatusEnum;
import com.lee.exception.BusinessUnitExistedException;
import com.lee.exception.BusinessUnitNotExistedException;
import com.lee.exception.TenantNotExistedException;
import com.lee.service.BusinessUnitService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 */
@Slf4j
@RestController
@RequestMapping("/businessUnit")
public class BusinessUnitController {
    private static final Long DEFAULT_TENANT_ID = 0L;
    @Resource
    private BusinessUnitService service;
    @Resource
    private ModelMapper modelMapper;

    /**
     * 查询分部列表
     *
     * @param tenantId 租户
     * @return
     */
    @GetMapping("list")
    public BaseResponse<List<BusinessUnitVO>> findBusinessUnitByTenantId(@RequestHeader("authorization") String authorization,
                                                                         @RequestParam(value = "tenantId",
                                                                                 required = false) Long tenantId) {
        if (tenantId == null || tenantId == 0L) {
            tenantId = DEFAULT_TENANT_ID;
        }
        List<BusinessUnitDTO> list = service.findBusinessUnitByTenantId(tenantId);
        List<BusinessUnitVO> voList = new ArrayList<>();
        list.forEach(businessUnitDTO -> {
            BusinessUnitVO vo = modelMapper.map(businessUnitDTO, BusinessUnitVO.class);
            voList.add(vo);
        });
        return BaseResponse.ok(voList);
    }

    /**
     * 新增分部
     *
     * @param businessUnit 分部
     * @return 新增条数
     */
    @PostMapping
    public BaseResponse createBusinessUnit(@RequestBody BusinessUnitVO businessUnit)
            throws BusinessUnitExistedException {
        businessUnit.setStatus(EnabledStatusEnum.ENABLED);
        BusinessUnitDTO dto = modelMapper.map(businessUnit, BusinessUnitDTO.class);
        int count = service.createBusinessUnit(dto);
        return BaseResponse.ok(count);
    }

    /**
     * 根据ID更新分部
     *
     * @param businessUnit 分部
     * @return 更新条数
     * @throws BusinessUnitNotExistedException 分部不存在
     */
    @PutMapping
    public BaseResponse updateBusinessUnitById(@RequestBody BusinessUnitVO businessUnit) throws BusinessUnitNotExistedException {
        BusinessUnitDTO dto = modelMapper.map(businessUnit, BusinessUnitDTO.class);
        int count = service.updateBusinessUnitById(dto);
        return BaseResponse.ok(count);
    }

    /**
     * http://127.0.0.1:8300/user-api/BusinessUnit/2?access_token=52af721c-9425-4fee-a46b-8fee4f068ec8
     * 根据ID查询出分部
     *
     * @param id 分部ID
     * @return 分部
     */
    @GetMapping("/{id}")
    public BaseResponse findBusinessUnitById(@PathVariable("id") Long id) {
        BusinessUnitDTO businessUnitDTO = service.findBusinessUnitById(id);
        BusinessUnitVO businessUnitVO = modelMapper.map(businessUnitDTO, BusinessUnitVO.class);
        return BaseResponse.ok(businessUnitVO);
    }

    /**
     * 分页查询出租户下的分部
     *
     * @return 该分页下的分部
     * @throws TenantNotExistedException 租户
     */
    @GetMapping(value = "/page")
    public BaseResponse pageList(Pagination pagination, BusinessUnitVO businessUnitVO) {
        BusinessUnitDTO businessUnitDTO = modelMapper.map(businessUnitVO, BusinessUnitDTO.class);
        IPage<BusinessUnitDTO> iPage = service.pageList(pagination, businessUnitDTO);
        PaginationResponse response = PaginationResponseUtil.convertIPageToPagination(iPage);
        if (log.isDebugEnabled()) {
            log.debug("[BusinessUnitController pageList] result:" + response);
        }
        return BaseResponse.ok(response);
    }
}
