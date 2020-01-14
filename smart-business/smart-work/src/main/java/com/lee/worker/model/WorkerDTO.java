package com.lee.worker.model;

import com.lee.common.business.domain.BaseDTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lee.li
 */
@Data
public class WorkerDTO extends BaseDTO {
    private Long id;
    /**
     * 名称
     */
    private String name;

}
