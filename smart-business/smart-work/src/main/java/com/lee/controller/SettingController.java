package com.lee.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.lee.api.vo.BusinessUnitVO;
import com.lee.api.vo.SysUserVO;
import com.lee.enums.ErrorMsgEnum;
import com.lee.api.feign.RemoteUserClient;
import com.lee.common.core.response.BaseResponse;
import com.lee.enums.LineTypeEnum;
import com.lee.enums.PeriodEnum;
import com.lee.model.*;
import com.lee.service.BusinessUnitService;
import com.lee.service.PartnerService;
import com.lee.service.SettingService;
import com.lee.service.WorkerService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @author haitao Li
 */
@RestController
@RequestMapping("setting")
@Slf4j
public class SettingController {
    private static final String EXCEL_TEMPLATE_SHOP = SettingController.class.getResource("/").getPath() + "/templates" +
            "/work" +
            ".xlsx";
    @Resource
    private RemoteUserClient remoteUserClient;
    @Resource
    private BusinessUnitService businessUnitService;
    @Resource
    private PartnerService partnerService;
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private WorkerService workerService;
    @Resource
    private SettingService settingService;
    @Resource
    private RemoteUserClient businessUnitClient;

    @GetMapping("init")
    public BaseResponse<SettingReportInitVO> init(@RequestHeader("authorization") String authorization) {

        BaseResponse<List<BusinessUnitVO>> businessUnitRes = businessUnitClient.getBusinessUnits(authorization, true);
        BaseResponse<List<Period>> periodRes = this.getPeriods();

        List<BusinessUnitVO> businessUnits = businessUnitRes.getData();
        List<Period> periods = periodRes.getData();

        Long businessUnitId = businessUnits.get(0).getId();
        String type = periods.get(0).getType();

        LocalDate date = LocalDate.now();
        LocalDate beginDate = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = date.with(TemporalAdjusters.lastDayOfMonth());
        PeriodEnum periodTypeEnum = Enum.valueOf(PeriodEnum.class, type);
        if (log.isDebugEnabled()) {
            log.debug("SettingController,businessUnitReport,businessUnitId:{},periodTypeEnum:{}," +
                    "beginDate:{},endDate:{}", businessUnitId, periodTypeEnum, beginDate, endDate);
        }
        SettingReportDTO settingReportDTO = settingService.businessUnitReport(businessUnitId, periodTypeEnum,
                beginDate, endDate);
        SettingReportVO settingReportVO = convertSettingReportDTOToVO(settingReportDTO);

        SettingReportInitVO settingReportInitVO = new SettingReportInitVO();
        settingReportInitVO.setBusinessUnitList(businessUnitRes.getData());
        settingReportInitVO.setPeriodList(periodRes.getData());
        settingReportInitVO.setSettingReportVO(settingReportVO);
        return BaseResponse.ok(settingReportInitVO);
    }

    private SettingReportVO convertSettingReportDTOToVO(SettingReportDTO settingReportDTO) {
        SettingReportVO settingReportVO = new SettingReportVO();

        List<AmountReportDTO> amountReportDTOList = settingReportDTO.getAmountList();
        List<AmountReportVO> amountReportVOList = new ArrayList<>();

        amountReportDTOList.forEach(amountReportDTO -> {
            AmountReportVO amountReportVO = modelMapper.map(amountReportDTO, AmountReportVO.class);
            amountReportVOList.add(amountReportVO);
        });

        List<WorkTimeReportDTO> workTimeReportDTOList = settingReportDTO.getWorkTimeList();
        List<WorkTimeReportVO> workTimeReportVOList = new ArrayList<>();
        workTimeReportDTOList.forEach(workTimeReportDTO -> {
            WorkTimeReportVO workTimeReportVO = modelMapper.map(workTimeReportDTO, WorkTimeReportVO.class);
            workTimeReportVOList.add(workTimeReportVO);
        });

        List<WorkEfficiencyReportDTO> workEfficiencyReportDTOList = settingReportDTO.getWorkEfficiencyList();
        List<WorkEfficiencyReportVO> workEfficiencyReportVOList = new ArrayList<>();
        workEfficiencyReportDTOList.forEach(workEfficiencyReportDTO -> {
            WorkEfficiencyReportVO workEfficiencyReportVO = modelMapper.map(workEfficiencyReportDTO,
                    WorkEfficiencyReportVO.class);
            workEfficiencyReportVOList.add(workEfficiencyReportVO);
        });
        settingReportVO.setAmountList(amountReportVOList);
        settingReportVO.setWorkTimeList(workTimeReportVOList);
        settingReportVO.setWorkEfficiencyList(workEfficiencyReportVOList);
        settingReportVO.setBusinessUnitId(settingReportDTO.getBusinessUnitId());
        settingReportVO.setBusinessPeriodType(settingReportDTO.getBusinessPeriodType());
        settingReportVO.setBeginDate(settingReportDTO.getBeginDate());
        settingReportVO.setEndDate(settingReportDTO.getEndDate());
        return settingReportVO;
    }

    /**
     * 营业时段集合
     *
     * @return 营业时段
     */
    @GetMapping("businessPeriod")
    public BaseResponse<List<Period>> getPeriods() {
        List<Period> periods = new ArrayList<>();
        for (PeriodEnum period : PeriodEnum.values()) {
            Period period1 = new Period();
            period1.setType(period.name());
            period1.setDescription(period.getDescription());
            periods.add(period1);
        }
        return BaseResponse.ok(periods);
    }

    /**
     * 更新排班
     *
     * @param authorization 授权信息
     * @param settingVO     排班信息
     * @return
     */
    @PostMapping
    public BaseResponse saveSetting(@RequestHeader("authorization") String authorization,
                                    @RequestBody SettingVO settingVO) {
        Long businessUnitId = getBusinessUnitIdByAuthorization(authorization);
        if (businessUnitId == null) {
            ErrorMsgEnum errorMsgEnum = ErrorMsgEnum.NOT_FIND_BUSINESS_UNIT_BY_USER;
            return BaseResponse.error(errorMsgEnum.getCode(), errorMsgEnum.getMessage());
        }
        SettingDTO settingDTO = convertVoToDto(settingVO, businessUnitId);
        if (log.isDebugEnabled()) {
            log.debug("[Setting Controller],settingVO: {},settingDTO:{}", settingVO, settingDTO);
        }
        settingService.mergeSetting(settingDTO);
        return BaseResponse.ok("success");
    }

    /**
     * 转换VO to DTO
     *
     * @param settingVO
     * @return
     */
    private SettingDTO convertVoToDto(SettingVO settingVO, Long businessUnitId) {
        List<BusinessUnitDataVO> businessUnitDataVOList = settingVO.getBusinessUnitDataList();
        List<PartnerDataVO> partnerDataVOS = settingVO.getPartnerReportList();

        List<BusinessUnitDataDTO> businessUnitDataDTOList = new ArrayList<>();
        List<PartnerDataDTO> partnerDataDTOS = new ArrayList<>();
        SettingDTO settingDTO = new SettingDTO();
        businessUnitDataVOList.stream().forEach(businessUnitDataVO -> {
            businessUnitDataVO.setBusinessUnitId(businessUnitId);
            businessUnitDataVO.setReportDate(settingVO.getReportDate());
            BusinessUnitDataDTO businessUnitDataDTO = modelMapper.map(businessUnitDataVO, BusinessUnitDataDTO.class);
            businessUnitDataDTOList.add(businessUnitDataDTO);
        });
        partnerDataVOS.stream().forEach(partnerDataVO -> {
            partnerDataVO.setBusinessUnitId(businessUnitId);
            partnerDataVO.setReportDate(settingVO.getReportDate());
            PartnerDataDTO partnerDataDTO1 = modelMapper.map(partnerDataVO, PartnerDataDTO.class);
            partnerDataDTOS.add(partnerDataDTO1);
        });
        settingDTO.setBusinessUnitList(businessUnitDataDTOList);
        settingDTO.setPartnerReportList(partnerDataDTOS);
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
        if (log.isDebugEnabled()) {
            log.debug("[SettingController] getBusinessUnitByAuthorization,param:{}", authorization);
        }
        BaseResponse<SysUserVO> userBaseResponse = remoteUserClient.getCurrentUser(authorization);
        if (userBaseResponse == null) {
            return null;
        }
        if (log.isDebugEnabled()) {
            log.debug("[Setting Controller],userClient: response: {}", userBaseResponse);
        }
        SysUserVO sysUser = userBaseResponse.getData();
        if (sysUser == null) {
            return null;
        }
        //获取当前用户信息分布场所
        BusinessUnitVO businessUnit = userBaseResponse.getData().getBusinessUnit();
        if (businessUnit == null) {
            return null;
        }
        return businessUnit.getId();
    }

    /**
     * 查询排班信息
     *
     * @param authorization
     * @param reportDate
     * @return
     */
    @GetMapping
    private BaseResponse<SettingVO> getSettingData(@RequestHeader("authorization") String authorization,
                                                   @RequestParam(value = "reportDate", required = false) String reportDate,
                                                   @RequestParam(value = "businessUnitId") Long businessUnitId) {
        //报表请求日期为null,设置为当前日期
        LocalDate localDate = LocalDate.now();
        if (StringUtils.isNotEmpty(reportDate)) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(reportDate, fmt);
        }

        List<BusinessUnitDataVO> businessUnitDataVOS = dealBusinessUnitData(businessUnitId, localDate);
        List<PartnerDataVO> partnerDataVOS = dealPartnerData(businessUnitId, localDate);

        SettingVO settingVO = new SettingVO();
        settingVO.setReportDate(localDate);
        settingVO.setBusinessUnitDataList(businessUnitDataVOS);
        settingVO.setPartnerReportList(partnerDataVOS);
        return BaseResponse.ok(settingVO);
    }

    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse response, @RequestHeader("authorization") String authorization,
                      @RequestParam(value = "reportDate") String reportDate,
                      @RequestParam(value = "businessUnitId") Long businessUnitId) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(reportDate, fmt);
        List<BusinessUnitDataVO> businessUnitDataVOS = dealBusinessUnitData(businessUnitId, localDate);
        List<PartnerDataVO> partnerDataVOS = dealPartnerData(businessUnitId, localDate);
        List<BusinessUnitDataVO> data1 = new ArrayList<>();
        List<BusinessUnitDataVO> data2 = new ArrayList<>();
        List<BusinessUnitDataVO> data3 = new ArrayList<>();
        List<BusinessUnitDataVO> data4 = new ArrayList<>();
        List<BusinessUnitDataVO> data5 = new ArrayList<>();
        List<BusinessUnitDataVO> data6 = new ArrayList<>();
        if (businessUnitDataVOS != null) {
            businessUnitDataVOS.forEach(businessUnitDataVO -> {
                amountNullToZero(businessUnitDataVO);
                if (LineTypeEnum.PREDICT_AMOUNT.equals(businessUnitDataVO.getLineType())) {
                    data1.add(businessUnitDataVO);
                }
                if (LineTypeEnum.PREDICT_WORK_TIMES.equals(businessUnitDataVO.getLineType())) {
                    data2.add(businessUnitDataVO);
                }
                if (LineTypeEnum.PREDICT_WORK_EFFICIENCY.equals(businessUnitDataVO.getLineType())) {
                    data3.add(businessUnitDataVO);
                }
                if (LineTypeEnum.REAL_AMOUNT.equals(businessUnitDataVO.getLineType())) {
                    data4.add(businessUnitDataVO);
                }
                if (LineTypeEnum.REAL_WORK_TIMES.equals(businessUnitDataVO.getLineType())) {
                    data5.add(businessUnitDataVO);
                }
                if (LineTypeEnum.REAL_WORK_EFFICIENCY.equals(businessUnitDataVO.getLineType())) {
                    data6.add(businessUnitDataVO);
                }
            });
        }
        if (partnerDataVOS != null) {
            int i = 0;
            Iterator<PartnerDataVO> ite = partnerDataVOS.iterator();
            while (ite.hasNext()) {
                PartnerDataVO vo = ite.next();
                vo.setIndex(++i);
                Float workTimes = getWorkTimesFromTypes(vo);
                vo.setWorkTimes(workTimes);
            }
        }
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.addHeader("Content-Disposition", "attachment;filename=fileName" + ".xlsx");

        ExcelWriter excelWriter =
                EasyExcel.write(response.getOutputStream()).excelType(ExcelTypeEnum.XLSX).withTemplate(EXCEL_TEMPLATE_SHOP).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();

        excelWriter.fill(new FillWrapper("data1", data1), writeSheet);
        excelWriter.fill(new FillWrapper("data2", data2), writeSheet);
        excelWriter.fill(new FillWrapper("data3", data3), writeSheet);
        excelWriter.fill(new FillWrapper("data4", data4), writeSheet);
        excelWriter.fill(new FillWrapper("data5", data5), writeSheet);
        excelWriter.fill(new FillWrapper("data6", data6), writeSheet);
        excelWriter.fill(new FillWrapper("data7", partnerDataVOS), writeSheet);

        excelWriter.finish();
    }

    private void amountNullToZero(BusinessUnitDataVO businessUnitDataVO) {
        if (businessUnitDataVO.getBreakfastAmount() == null) {
            businessUnitDataVO.setBreakfastAmount(0d);
        }
        if (businessUnitDataVO.getLunchAmount() == null) {
            businessUnitDataVO.setLunchAmount(0d);
        }
        if (businessUnitDataVO.getSupperFirstPhaseAmount() == null) {
            businessUnitDataVO.setSupperFirstPhaseAmount(0d);
        }
        if (businessUnitDataVO.getSupperSecondPhaseAmount() == null) {
            businessUnitDataVO.setSupperSecondPhaseAmount(0d);
        }
        if (businessUnitDataVO.getSupperThirdPhaseAmount() == null) {
            businessUnitDataVO.setSupperThirdPhaseAmount(0d);
        }
    }

    private Float getWorkTimesFromTypes(PartnerDataVO partnerDataVO) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Float count = 0f;
        Method[] methods = partnerDataVO.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            String name = methods[i].getName();
            if (StringUtils.startsWith(name, "getType")) {
                String type = (String) MethodUtils.invokeMethod(partnerDataVO, name);
                if (StringUtils.isNotBlank(type)) {
                    count = count + 0.5f;
                }
            }
        }
        return count;
    }

    /**
     * 处理分部数据,
     *
     * @param businessUnitId 分部ID
     * @param reportDate     查询日期
     * @return
     */
    private List<BusinessUnitDataVO> dealBusinessUnitData(Long businessUnitId, LocalDate reportDate) {
        List<BusinessUnitDataVO> list = new ArrayList<>();

        List<BusinessUnitDataDTO> businessUnitDataDTOS =
                businessUnitService.getBusinessUnitById(businessUnitId, reportDate);
        if (businessUnitDataDTOS == null || businessUnitDataDTOS.size() == 0) {
            return null;
        }
        for (int i = 0; i < businessUnitDataDTOS.size(); i++) {
            BusinessUnitDataVO businessUnitDataVO = modelMapper.map(businessUnitDataDTOS.get(i),
                    BusinessUnitDataVO.class);
            list.add(businessUnitDataVO);
        }
        return list;
    }


    /**
     * 处理伙伴排班信息
     *
     * @param businessUnitId 分部ID
     * @param reportDate     报告日期
     * @return 查询结果
     */
    private List<PartnerDataVO> dealPartnerData(Long businessUnitId, LocalDate reportDate) {

        PartnerDataDTO dto = new PartnerDataDTO();
        dto.setBusinessUnitId(businessUnitId);
        dto.setReportDate(reportDate);
        List<PartnerDataDTO> list = partnerService.list(dto);

        List<PartnerDataVO> partnerDataVOS = new ArrayList<>();
        //报表中无数据,根据伙伴信息,返回 只包含伙伴ID的 虚拟空数据
        if (list == null || list.size() == 0) {
            WorkerDTO workerDTO = new WorkerDTO();
            workerDTO.setBusinessUnitId(businessUnitId);
            List<WorkerDTO> workerDTOS = workerService.list(workerDTO);
            workerDTOS.forEach(workerDTO1 -> {
                PartnerDataVO partnerDataVO = new PartnerDataVO();
                partnerDataVO.setBusinessUnitId(businessUnitId);
                partnerDataVO.setPartnerId(workerDTO1.getId());
                partnerDataVO.setPartnerName(workerDTO1.getName());
                partnerDataVOS.add(partnerDataVO);
            });
        } else {
            //转换成对外信息
            list.forEach(settingPartnerDataDTO -> {
                PartnerDataVO partnerDataVO = modelMapper.map(settingPartnerDataDTO, PartnerDataVO.class);
                WorkerDTO workerDTO = workerService.getById(settingPartnerDataDTO.getPartnerId());
                if (log.isDebugEnabled()) {
                    log.debug("[SettingController] dealResponse,workerDTO:{}", workerDTO);
                }
                partnerDataVO.setPartnerId(workerDTO.getId());
                partnerDataVO.setPartnerName(workerDTO.getName());
                if (partnerDataVO.getType0830() == null) {
                    partnerDataVO.setType0830(" ");
                }
                partnerDataVOS.add(partnerDataVO);
            });
        }
        return partnerDataVOS;
    }

    /**
     * 分部展示报表
     *
     * @param businessUnitId 分部ID
     * @param periodType     营业时段类型
     * @param beginDate      查询开始时间
     * @param endDate        查询截至时间
     * @return 报表
     */
    @GetMapping("report")
    public BaseResponse<SettingReportVO> report(@RequestParam("businessUnitId") Long businessUnitId,
                                                @RequestParam("businessPeriodType") String periodType,
                                                @RequestParam("beginDate") String beginDate,
                                                @RequestParam("endDate") String endDate) {
        SettingReportVO settingReportVO;
        PeriodEnum periodTypeEnum = Enum.valueOf(PeriodEnum.class, periodType);
        ;
        SettingReportDTO settingReportDTO = settingService.businessUnitReport(businessUnitId, periodTypeEnum,
                LocalDate.parse(beginDate), LocalDate.parse(endDate));
        settingReportVO = modelMapper.map(settingReportDTO, SettingReportVO.class);
        return BaseResponse.ok(settingReportVO);
    }


}
