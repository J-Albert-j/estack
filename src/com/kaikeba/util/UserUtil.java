package com.kaikeba.util;

import com.kaikeba.bean.Courier;
import com.kaikeba.bean.User;

import javax.servlet.http.HttpSession;

public class UserUtil {
    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }
    public static String getUserPhone(HttpSession session){
        // TODO : 还没有编写柜子端,未存储任何的录入人信息
        return "18888888888";
    }

    /**
     * 获取登录验证码
     * @param session
     * @param userPhone
     * @return
     */
    public static String getLoginSms(HttpSession session, String userPhone){
        return (String) session.getAttribute(userPhone);
    }

    /**
     * 获取修改信息验证码
     * @param session
     * @param userPhone
     * @return
     */
    public static String getUpdateSms(HttpSession session, String userPhone){
        return (String) session.getAttribute(userPhone+"_update");
    }

    /**
     * 设置登录验证码
     * @param session
     * @param userPhone
     * @param code
     */
    public static void setLoginSms(HttpSession session, String userPhone, String code){
        session.setAttribute(userPhone, code);
    }

    /**
     * 设置修改验证码
     * @param session
     * @param userPhone
     * @param code
     */
    public static void setUpdateSms(HttpSession session, String userPhone, String code){
        session.setAttribute(userPhone+"_update", code);
    }

    /**
     * 方法重载，当前登录的可能是用户也可能是快递员
     * @param session
     * @param user
     */
    public static void setWxUser(HttpSession session, User user){
        session.setAttribute("wxUser", user);
    }
    public static void setWxUser(HttpSession session, Courier courier){
        session.setAttribute("wxUser", courier);
    }

    /**
     * 获取当前登录的用户or快递员
     * @param session
     * @return
     */
    public static Object getWxUser(HttpSession session){
        return session.getAttribute("wxUser");
    }
}
