package com.lee.setting.controller;

import com.lee.ErrorMsgEnum;
import com.lee.api.entity.SysUser;
import com.lee.api.feign.UserClient;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.WebUtil;
import com.lee.setting.model.*;
import com.lee.setting.service.SettingBusinessUnitService;
import com.lee.setting.service.SettingPartnerService;
import com.lee.setting.service.SettingService;
import com.lee.worker.model.WorkerDTO;
import com.lee.worker.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 */
@RestController
@RequestMapping("setting")
@Slf4j
public class SettingController {
    @Resource
    private UserClient userClient;
    @Resource
    private SettingBusinessUnitService settingBusinessUnitService;
    @Resource
    private SettingPartnerService settingPartnerService;
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private WorkerService workerService;
    @Resource
    private SettingService settingService;

    /**
     * 更新排班
     *
     * @param authorization
     * @param settingVO
     * @return
     */
    @PostMapping
    public BaseResponse<String> saveSetting(@RequestHeader("authorization") String authorization,
                                            @RequestBody SettingVO settingVO) {
        if (StringUtils.isEmpty(authorization)) {
            ErrorMsgEnum errorMsgEnum = ErrorMsgEnum.AUTHORIZATION_ERROR;
            return BaseResponse.error(errorMsgEnum.getCode(), errorMsgEnum.getMessage());
        }

        Long businessUnitId = getBusinessUnitIdByAuthorization(authorization);
        if (businessUnitId == null) {
            ErrorMsgEnum errorMsgEnum = ErrorMsgEnum.AUTHORIZATION_ERROR;
            return BaseResponse.error(errorMsgEnum.getCode(), errorMsgEnum.getMessage());
        }
        SettingDTO settingDTO = convertVoToDto(settingVO);
        settingService.mergeSetting(settingDTO);
        return null;
    }

    /**
     * 转换VO to DTO
     *
     * @param settingVO
     * @return
     */
    private SettingDTO convertVoToDto(SettingVO settingVO) {
        List<SettingBusinessUnitDataVO> settingBusinessUnitDataVOS = settingVO.getBusinessUnitList();
        List<SettingPartnerDataVO> settingPartnerDataVOS = settingVO.getPartnerReportList();

        List<SettingBusinessUnitDataDTO> settingBusinessUnitDataDTOS = new ArrayList<>();
        List<SettingPartnerDataDTO> settingPartnerDataDTOS = new ArrayList<>();
        SettingDTO settingDTO = new SettingDTO();
        settingBusinessUnitDataVOS.stream().forEach(settingBusinessUnitDataVO -> {
            settingBusinessUnitDataVO.setReportDate(settingVO.getReportDate());
            SettingBusinessUnitDataDTO settingBusinessUnitDataDTO = modelMapper.map(settingBusinessUnitDataVO, SettingBusinessUnitDataDTO.class);
            settingBusinessUnitDataDTOS.add(settingBusinessUnitDataDTO);
        });
        settingPartnerDataVOS.stream().forEach(settingPartnerDataVO -> {
            settingPartnerDataVO.setReportDate(settingVO.getReportDate());
            SettingPartnerDataDTO settingPartnerDataDTO1 = modelMapper.map(settingPartnerDataVO, SettingPartnerDataDTO.class);
            settingPartnerDataDTOS.add(settingPartnerDataDTO1);
        });
        settingDTO.setBusinessUnitList(settingBusinessUnitDataDTOS);
        settingDTO.setPartnerReportList(settingPartnerDataDTOS);
        return settingDTO;
    }

    /**
     * 根据授权信息获取业务单元ID
     *
     * @param authorization 授权信息
     * @return
     */
    private Long getBusinessUnitIdByAuthorization(String authorization) {
        if (StringUtils.isEmpty(authorization)) {
            return null;
        }
        String accessToken = WebUtil.getAccessToken(authorization);
        BaseResponse<SysUser> userBaseResponse = userClient.getCurrentUser(accessToken);
        if (userBaseResponse == null) {
            return null;
        }
        if (log.isDebugEnabled()) {
            log.debug("[Setting Controller],userClient: response: {}", userBaseResponse);
        }
        SysUser sysUser = userBaseResponse.getData();
        if (sysUser == null) {
            return null;
        }
        //获取当前用户信息分布场所
        return userBaseResponse.getData().getBusinessUnitId();
    }

    /**
     * 得到业务单元数据
     *
     * @param authorization 授权信息
     * @param reportDate    报表日期,默认为服务器当前日期
     * @return
     */
    @GetMapping("businessUnit")
    public BaseResponse<SettingBusinessUnitDataVO> getBusinessUnitData(@RequestHeader("authorization") String authorization,
                                                                       @RequestParam(value = "reportDate", required = false) String reportDate) {
        Long businessUnitId = getBusinessUnitIdByAuthorization(authorization);
        if (businessUnitId == null) {
            ErrorMsgEnum error = ErrorMsgEnum.AUTHORIZATION_ERROR;
            return BaseResponse.error(error.getCode(), error.getMessage());
        }
        //报表请求日期为null,设置为当前日期
        LocalDate localDate = LocalDate.now();
        if (StringUtils.isNotEmpty(reportDate)) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(reportDate, fmt);
        }
        SettingBusinessUnitDataDTO settingBusinessUnitDataDTO =
                settingBusinessUnitService.getBusinessUnitByBusinessUnitId(businessUnitId, localDate);
        SettingBusinessUnitDataVO vo = null;
        if (settingBusinessUnitDataDTO == null) {
            vo = new SettingBusinessUnitDataVO();
            vo.setBusinessUnitId(businessUnitId);
            vo.setReportDate(localDate);
        } else {
            vo = modelMapper.map(settingBusinessUnitDataDTO, SettingBusinessUnitDataVO.class);
        }
        return BaseResponse.ok(vo);
    }

    /**
     * 得到业务单元数据
     *
     * @param authorization 授权信息
     * @param reportDate    报表日期,默认为服务器当前日期
     * @return
     */
    @GetMapping("partner")
    public BaseResponse<List<SettingPartnerDataVO>> getPartnerData(@RequestHeader("authorization") String authorization,
                                                                   @RequestParam(value = "reportDate", required = false) String reportDate) {
        //报表请求日期为null,设置为当前日期
        LocalDate localDate = LocalDate.now();
        if (StringUtils.isNotEmpty(reportDate)) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(reportDate, fmt);
        }

        //授权信息获取部门ID
        Long businessUnitId = getBusinessUnitIdByAuthorization(authorization);
        if (businessUnitId == null) {
            ErrorMsgEnum errorMsgEnum = ErrorMsgEnum.AUTHORIZATION_ERROR;
            return BaseResponse.error(errorMsgEnum.getCode(), errorMsgEnum.getMessage());
        }

        SettingPartnerDataDTO dto = new SettingPartnerDataDTO();
        dto.setBusinessUnitId(businessUnitId);
        dto.setReportDate(localDate);
        List<SettingPartnerDataDTO> list = settingPartnerService.list(dto);
        return dealResponse(businessUnitId, list);
    }

    private BaseResponse<List<SettingPartnerDataVO>> dealResponse(Long businessUnitId, List<SettingPartnerDataDTO> list) {
        List<SettingPartnerDataVO> settingPartnerDataVOS = new ArrayList<>();
        //报表中无数据,根据伙伴信息,返回 只包含伙伴ID的 虚拟空数据
        if (list == null || list.size() == 0) {
            WorkerDTO workerDTO = new WorkerDTO();
            workerDTO.setBusinessUitId(businessUnitId);
            List<WorkerDTO> workerDTOS = workerService.list(workerDTO);
            if (workerDTOS == null) {
                ErrorMsgEnum errorMsgEnum = ErrorMsgEnum.NO_PARTNER_DATA;
                return BaseResponse.error(errorMsgEnum.getCode(), errorMsgEnum.getMessage());
            }
            workerDTOS.stream().forEach(workerDTO1 -> {
                SettingPartnerDataVO settingPartnerDataVO = new SettingPartnerDataVO();
                settingPartnerDataVO.setBusinessUnitId(businessUnitId);
                settingPartnerDataVO.setPartnerId(workerDTO1.getId());
                settingPartnerDataVO.setPartnerName(workerDTO1.getName());
                settingPartnerDataVOS.add(settingPartnerDataVO);
            });
        } else {
            //转换成对外信息
            list.stream().forEach(settingPartnerDataDTO -> {
                SettingPartnerDataVO settingPartnerDataVO = modelMapper.map(settingPartnerDataDTO, SettingPartnerDataVO.class);
                WorkerDTO workerDTO = workerService.getById(settingPartnerDataDTO.getPartnerId());
                settingPartnerDataVO.setPartnerId(workerDTO.getId());
                settingPartnerDataVO.setPartnerName(workerDTO.getName());
                settingPartnerDataVOS.add(settingPartnerDataVO);
            });
        }
        return BaseResponse.ok(settingPartnerDataVOS);
    }
}
