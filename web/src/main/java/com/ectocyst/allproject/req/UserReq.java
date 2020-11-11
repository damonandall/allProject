package com.ectocyst.allproject.req;

import lombok.Data;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 21:16 2020-11-10
 * @Modified By:
 */
public class UserReq {

    private String userName;

    private Long mobile;

    private String roomNum;

    private String signDay;

    private Integer time;

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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
