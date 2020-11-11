package com.ectocyst.allproject.dao.DO;

import lombok.Data;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 21:02 2020-11-10
 * @Modified By:
 */
public class UserDO {

    private Long id ;

    private String userName;

    private Long mobile;

    private String roomNum;

    private String signDay;

    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getSignDay() {
        return signDay;
    }

    public void setSignDay(String signDay) {
        this.signDay = signDay;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
