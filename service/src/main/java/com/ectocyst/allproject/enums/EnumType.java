package com.ectocyst.allproject.enums;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 22:41 2020-11-10
 * @Modified By:
 */
public enum EnumType {

    WANG_CHENG_FU(1,"望辰府");

    private Integer type;
    private String desc;

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    EnumType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
