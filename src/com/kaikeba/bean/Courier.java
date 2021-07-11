package com.kaikeba.bean;
//1.数据库中建表
//2.定义实体类
import java.sql.Timestamp;

/**
 * @author JJH
 * @2021/7/3 15:01
 * 说明：
 */
public class Courier {
    private int id;
    private String courierName;
    private String courierPhone;
    private String courierIdNumber;
    private String courierPassword;
    private int courierSendNumber;
    private Timestamp enrollTime;
    private Timestamp loginTime;

    public Courier() {
    }

    public Courier(String courierName, String courierPhone, String courierIdNumber, String courierPassword) {
        this.courierName = courierName;
        this.courierPhone = courierPhone;
        this.courierIdNumber = courierIdNumber;
        this.courierPassword = courierPassword;
    }

    public Courier(int id, String courierName, String courierPhone, String courierIdNumber, String courierPassword, int courierSendNumber, Timestamp enrollTime, Timestamp loginTime) {
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

    public int getCourierSendNumber() {
        return courierSendNumber;
    }

    public void setCourierSendNumber(int courierSendNumber) {
        this.courierSendNumber = courierSendNumber;
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
        return "Courier{" +
                "id=" + id +
                ", courierName='" + courierName + '\'' +
                ", courierPhone='" + courierPhone + '\'' +
                ", courierIdNumber='" + courierIdNumber + '\'' +
                ", courierPassword='" + courierPassword + '\'' +
                ", courierSendNumber=" + courierSendNumber +
                ", enrollTime=" + enrollTime +
                ", loginTime=" + loginTime +
                '}';
    }
}
