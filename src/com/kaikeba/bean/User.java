package com.kaikeba.bean;

import java.sql.Timestamp;

/**
 * @author JJH
 * @2021/6/30 16:25
 * 说明：
 */
public class User {
    private int id;
    private String userName;
    private String userPassword;
    private String userIdNumber;
    private String userPhone;
    private Timestamp enrollTime;
    private Timestamp loginTime;

    //无参构造
    public User() {
    }

    public User(String userName, String userPassword, String userIdNumber,String userPhone) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userIdNumber = userIdNumber;
        this.userPassword = userPassword;

    }

    public User(String userName, String userPassword, String userIdNumber, String userPhone, Timestamp enrollTime, Timestamp loginTime) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIdNumber = userIdNumber;
        this.userPhone = userPhone;
        this.enrollTime = enrollTime;
        this.loginTime = loginTime;
    }

    //全参构造
    public User(int id, String userName, String userPassword, String userIdNumber, String userPhone, Timestamp enrollTime, Timestamp loginTime) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIdNumber = userIdNumber;
        this.userPhone = userPhone;
        this.enrollTime = enrollTime;
        this.loginTime = loginTime;
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

    public Timestamp getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Timestamp enrollTime) {
        this.enrollTime = enrollTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userIdNumber='" + userIdNumber + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", enrollTime=" + enrollTime +
                ", loginTime=" + loginTime +
                '}';
    }
}
