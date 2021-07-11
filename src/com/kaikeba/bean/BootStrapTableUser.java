package com.kaikeba.bean;

import java.sql.Timestamp;

public class BootStrapTableUser {
    private int id;
    private String userName;
    private String userPassword;
    private String userIdNumber;
    private String userPhone;
    private String enrollTime;
    private String loginTime;

    public BootStrapTableUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserIdNumber() {
        return userIdNumber;
    }

    public void setUserIdNumber(String userIdNumber) {
        this.userIdNumber = userIdNumber;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public BootStrapTableUser(int id, String userName, String userPassword, String userIdNumber, String userPhone, String enrollTime, String loginTime) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIdNumber = userIdNumber;
        this.userPhone = userPhone;
        this.enrollTime = enrollTime;
        this.loginTime = loginTime;
    }
}