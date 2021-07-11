package com.kaikeba.bean;

/**
 * 2 * @Author: 小王同学
 * 3 * @Date: 2020/10/14 9:17
 * 4   用于显示懒人榜的类
 */
public class LazyBoardUser implements Comparable<LazyBoardUser>{

    private String userPhone;
    private String userName;
    private int expressNumber;

    public LazyBoardUser() {
    }

    public LazyBoardUser(String userPhone, String userName, int expressNumber) {
        this.userPhone = userPhone;
        this.userName = userName;
        this.expressNumber = expressNumber;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(int expressNumber) {
        this.expressNumber = expressNumber;
    }

    @Override
    public int compareTo(LazyBoardUser lu){
        // 先按照快递数量排序
        int i = lu.getExpressNumber() - this.getExpressNumber();
        if (i == 0){
            return this.getUserName().compareTo(lu.getUserName());
        }
        return i;
    }

    @Override
    public String toString() {
        return "LazyBoardUser{" +
                "userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", expressNumber=" + expressNumber +
                '}';
    }
}
