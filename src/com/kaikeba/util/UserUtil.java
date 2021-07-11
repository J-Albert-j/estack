package com.kaikeba.util;

import javax.servlet.http.HttpSession;

public class UserUtil {
    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }
    public static String getUserPhone(HttpSession session){
        // TODO : 还没有编写柜子端,未存储任何的录入人信息
        return "18888888888";
    }
}
