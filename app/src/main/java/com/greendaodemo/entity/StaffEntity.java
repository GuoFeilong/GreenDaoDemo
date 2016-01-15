package com.greendaodemo.entity;

/**
 * Created by jsion on 16/1/14.
 */
public class StaffEntity {
    private String staffName;
    private String staffNum;
    private Integer staffAge;
    private String motto;
    private Long insertTime;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public Integer getStaffAge() {
        return staffAge;
    }

    public void setStaffAge(Integer staffAge) {
        this.staffAge = staffAge;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Long insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        return "StaffEntity{" +
                "staffName='" + staffName + '\'' +
                ", staffNum='" + staffNum + '\'' +
                ", staffAge=" + staffAge +
                ", motto='" + motto + '\'' +
                ", insertTime=" + insertTime +
                '}';
    }
}
