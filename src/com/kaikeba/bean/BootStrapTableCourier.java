package com.kaikeba.bean;

/**
 * @author JJH
 * @2021/7/3 15:26
 * 说明：
 */
public class BootStrapTableCourier {
    private int id;
    private String courierName;
    private String courierPhone;
    private String courierIdNumber;
    private String courierPassword;
    private String courierSendNumber;
    private String enrollTime;
    private String loginTime;

    public BootStrapTableCourier() {
    }

    public BootStrapTableCourier(int id, String courierName, String courierPhone, String courierIdNumber, String courierPassword, String courierSendNumber, String enrollTime, String loginTime) {
        this.id = id;
        this.courierName = courierName;
        this.courierPhone = courierPhone;
        this.courierIdNumber = courierIdNumber;
        this.courierPassword = courierPassword;
        this.courierSendNumber = courierSendNumber;
        this.enrollTime = enrollTime;
        this.loginTime = loginTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public String getCourierIdNumber() {
        return courierIdNumber;
    }

    public void setCourierIdNumber(String courierIdNumber) {
        this.courierIdNumber = courierIdNumber;
    }

    public String getCourierPassword() {
        return courierPassword;
    }

    public void setCourierPassword(String courierPassword) {
        this.courierPassword = courierPassword;
    }

    public String getCourierSendNumber() {
        return courierSendNumber;
    }

    public void setCourierSendNumber(String courierSendNumber) {
        this.courierSendNumber = courierSendNumber;
    }

    public String getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(String enrollTime) {
        this.enrollTime = enrollTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
