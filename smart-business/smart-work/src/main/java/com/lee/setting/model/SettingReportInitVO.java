package com.lee.setting.model;

import com.lee.api.entity.BusinessUnit;
import lombok.Data;

import java.util.List;

/**
 * @author lee.li
 */
@Data
public class SettingReportInitVO {
    private List<BusinessUnit> businessUnitList;
    private List<Period> periodList;
    private SettingReportVO settingReportVO;
}
