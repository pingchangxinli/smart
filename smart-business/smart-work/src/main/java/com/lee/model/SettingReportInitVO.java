package com.lee.model;

import com.lee.api.vo.BusinessUnitVO;
import lombok.Data;

import java.util.List;

/**
 * @author haitao Li
 */
@Data
public class SettingReportInitVO {
    private List<BusinessUnitVO> businessUnitList;
    private List<Period> periodList;
    private SettingReportVO settingReportVO;
}
