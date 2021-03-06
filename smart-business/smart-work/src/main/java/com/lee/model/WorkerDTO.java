package com.lee.model;

import com.lee.common.business.domain.BaseDTO;
import lombok.Data;

/**
 * @author haitao Li
 */
@Data
public class WorkerDTO extends BaseDTO {
    private Long id;
    /**
     * 名称
     */
    private String name;

}
