package com.lee.enums;

/**
 * @author lee.li
 */
public enum BusinessEnum {
    PREDICT("预估"), REAL("实际");
    private String desc;

    BusinessEnum(String desc) {
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }
}
