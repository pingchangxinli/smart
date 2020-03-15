package com.lee.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author lee.li
 */
@Data
public class AmountReportDTO {
    private LocalDate reportDate;
    private String type;
    private String typeDesc;
    private Double amount;
}
