package com.lee.setting.service;

import com.lee.common.business.service.BaseService;
import com.lee.setting.model.SettingDTO;
import org.springframework.stereotype.Service;

/**
 * @author lee.li
 */

public interface SettingService extends BaseService<SettingDTO> {
    /**
     * 新增或者修改排便报表
     *
     * @param settingDTO
     */
    void mergeSetting(SettingDTO settingDTO);
}
