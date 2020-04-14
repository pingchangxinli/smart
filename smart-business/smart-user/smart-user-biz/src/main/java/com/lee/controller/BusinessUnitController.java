package com.lee.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.api.vo.BusinessUnitVO;
import com.lee.api.vo.SysUserVO;
import com.lee.common.business.domain.LoginUser;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.domain.BusinessUnitDTO;
import com.lee.domain.SysUserDTO;
import com.lee.enums.EnabledStatusEnum;
import com.lee.enums.UserErrorMessageTipEnum;
import com.lee.exception.BusinessUnitExistedException;
import com.lee.exception.BusinessUnitNotExistedException;
import com.lee.exception.TenantNotExistedException;
import com.lee.feign.TokenClient;
import com.lee.service.BusinessUnitService;
import com.lee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haitao Li
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
    @Resource
    private UserService userService;
    @Resource
    private TokenClient tokenClient;

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
     * 查询分部列表
     *
     * @param needAll 如果authorization获取不到分部信息，
     *                true: 返回所有的分部信息；
     *                false： 返回Null
     * @return
     */
    @GetMapping("listByAuth")
    public BaseResponse<List<BusinessUnitVO>> findBusinessUnitByAuth(@RequestHeader("authorization") String authorization,
                                                                     @RequestParam("needAll") Boolean needAll) {
        BusinessUnitDTO businessUnitDTO = this.getBusinessUnitByAuthorization(authorization);
        if (businessUnitDTO == null) {
            if (needAll) {
                List<BusinessUnitVO> businessUnitVOList = new ArrayList<>();
                List<BusinessUnitDTO> list = service.findBusinessUnit(null);
                list.forEach(businessUnitDTO1 -> {
                    BusinessUnitVO businessUnitVO = modelMapper.map(businessUnitDTO1, BusinessUnitVO.class);
                    businessUnitVOList.add(businessUnitVO);
                });
                return BaseResponse.ok(businessUnitVOList);
            }
            return BaseResponse.ok(null);
        }
        BusinessUnitVO businessUnitVO = modelMapper.map(businessUnitDTO, BusinessUnitVO.class);
        List<BusinessUnitVO> list = new ArrayList<>();
        list.add(businessUnitVO);
        return BaseResponse.ok(list);
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

    /**
     * 根据授权信息获取业务单元ID
     *
     * @param authorization 授权信息
     * @return
     */
    private BusinessUnitDTO getBusinessUnitByAuthorization(String authorization) {
        if (StringUtils.isEmpty(authorization)) {
            return null;
        }
        BaseResponse<LoginUser> baseResponse = tokenClient.findUserByAccessToken(authorization);
        LoginUser loginUser = baseResponse.getData();
        Long id = loginUser.getId();
        if (loginUser == null || id == null || NumberUtils.compare(id, 0) <= 0) {
            return null;
        }
        SysUserDTO sysUserDTO = userService.findUserById(id);
        if (sysUserDTO == null) {
            return null;
        }

        return service.findBusinessUnitById(sysUserDTO.getBusinessUnitId());
    }
}
