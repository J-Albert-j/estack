package com.kaikeba.wx.controller;

import com.kaikeba.bean.*;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.service.ExpressService;
import com.kaikeba.service.UserService;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.RandomUtil;
import com.kaikeba.util.SMSUtil;
import com.kaikeba.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JJH
 * @2021/7/11 16:40
 * 说明：
 */
public class UserController {
    @ResponseBody("/wx/loginSms.do")
    public String sendSms(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        // TODO:为了调试的时候不浪费钱，先把这两行注释了
        String code = RandomUtil.getCode() + "";
        boolean flag = SMSUtil.loginSMS(userPhone, code);

//        String code = "111111";
//        boolean flag = true;

        Message msg = new Message();
        if (flag){
            // 短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码已发送，请查收！");
        }else {
            // 短信发送失败
            msg.setStatus(-1);
            msg.setResult("验证码发送失败，请检查手机号或稍后再试");
        }
        UserUtil.setLoginSms(request.getSession(), userPhone, code);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/updateSms.do")
    public String updateSms(HttpServletRequest request, HttpServletResponse response){
        String newPhone = request.getParameter("newPhone");
        // TODO:为了调试的时候不浪费钱，先把这两行注释了
        String code = RandomUtil.getCode() + "";
        boolean flag = SMSUtil.loginSMS(newPhone, code);

//        String code = "222222";
//        boolean flag = true;

        Message msg = new Message();
        if (flag){
            // 短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码已发送，请查收！");
        }else {
            // 短信发送失败
            msg.setStatus(-1);
            msg.setResult("验证码发送失败，请检查手机号或稍后再试");
        }
        UserUtil.setUpdateSms(request.getSession(), newPhone, code);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        String userCode = request.getParameter("code");
        String sysCode = UserUtil.getLoginSms(request.getSession(), userPhone);
        Message msg = new Message();
        if (sysCode == null){
            msg.setStatus(-1);
            msg.setResult("手机号码未获取短信");
        }else if (sysCode.equals(userCode)){
            // 验证码一致
            // TODO:判断是快递员还是用户，如果都不是则默认注册为用户，如果都是则以快递员身份登录
            User user1 = UserService.findByUserPhone(userPhone);
            Courier courier1 = CourierService.findByPhone(userPhone);
            if (user1 == null && courier1 == null){
                // 新注册为用户
                msg.setStatus(0);
                User u = new User();
                u.setUserPhone(userPhone);
                UserService.insert(u);
                UserUtil.setWxUser(request.getSession(), u);
            }else if (user1 != null && courier1 != null){
                // 以快递员身份登录
                msg.setStatus(1);
                UserUtil.setWxUser(request.getSession(), courier1);
            }else if (user1 != null && courier1 == null){
                // 用户登录
                msg.setStatus(0);
                UserUtil.setWxUser(request.getSession(), user1);
            }else if (user1 == null && courier1 != null){
                // 快递员登录
                msg.setStatus(1);
                UserUtil.setWxUser(request.getSession(), courier1);
            }
        }else {
            // 验证码不一致
            msg.setStatus(-2);
            msg.setResult("验证码不一致，请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 判断当前登陆的是用户还是快递员
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response){
        Object loginUser = UserUtil.getWxUser(request.getSession());
        boolean isUser = loginUser instanceof User;
        Message msg = new Message();
        if (isUser){
            msg.setStatus(0);
            msg.setResult(((User)loginUser).getUserPhone());
        }else {
            msg.setStatus(1);
            msg.setResult(((Courier)loginUser).getCourierPhone());
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // 销毁session
        request.getSession().invalidate();
        Message msg = new Message(0);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 获取当前登录用户的用户名
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/userName.do")
    public String userName(HttpServletRequest request, HttpServletResponse response){
        Object loginUser = UserUtil.getWxUser(request.getSession());
        boolean isUser = loginUser instanceof User;
        Message msg = new Message();
        if (isUser){
            msg.setStatus(0);
            msg.setResult(((User)loginUser).getUserName());
        }else {
            msg.setStatus(1);
            msg.setResult(((Courier)loginUser).getCourierName());
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    /**
     * 修改个人信息页面
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/wx/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        String newName = request.getParameter("newName");
        String newPhone = request.getParameter("newPhone");
        String verify = request.getParameter("verify");

        String sysCode = UserUtil.getUpdateSms(request.getSession(), newPhone);
        Message msg = new Message();
        if (sysCode == null){
            msg.setStatus(-1);
            msg.setResult("手机号码未获取短信");
        }else if (sysCode.equals(verify)){
            Object wxUser = UserUtil.getWxUser(request.getSession());
            if (wxUser instanceof User){
                User oldUser = UserService.findByUserPhone(((User) wxUser).getUserPhone());
                int id = oldUser.getId();
                oldUser.setUserName(newName);
                oldUser.setUserPhone(newPhone);
                boolean flag = UserService.update(id, oldUser);
                if (flag){
                    msg.setStatus(0);
                    msg.setResult("修改成功");
                }else {
                    msg.setStatus(-3);
                    msg.setResult("修改失败");
                }
            }else if (wxUser instanceof Courier){
                Courier oldUser = CourierService.findByPhone(((Courier)wxUser).getCourierPhone());
                int id = oldUser.getId();
                oldUser.setCourierName(newName);
                oldUser.setCourierPhone(newPhone);
                boolean flag = CourierService.update(id, oldUser);
                if (flag){
                    msg.setStatus(0);
                    msg.setResult("修改成功");
                }else {
                    msg.setStatus(-3);
                    msg.setResult("修改失败");
                }
            }
        }else {
            // 验证码不一致
            msg.setStatus(-2);
            msg.setResult("验证码不一致，请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/lazyboardTotal.do")
    public String lazyboardTotal(HttpServletRequest request, HttpServletResponse response){
        List<User> userList = UserService.findAll(false, 0, 0);
        List<LazyBoardUser> result = new ArrayList<>();

        for (User u : userList){
            List<Express> byUserPhone = ExpressService.findByUserPhone(u.getUserPhone());
            LazyBoardUser lu = new LazyBoardUser(u.getUserPhone(), u.getUserName(), byUserPhone.size());
            result.add(lu);
        }

        Collections.sort(result);

        Message msg = new Message();
        if (!result.isEmpty()){
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(result);
        }else{
            msg.setStatus(-1);
            msg.setResult("查询失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/lazyboardYear.do")
    public String lazyboardYear(HttpServletRequest request, HttpServletResponse response){
        List<User> userList = UserService.findAll(false, 0, 0);
        List<LazyBoardUser> result = new ArrayList<>();

        for (User u : userList){
            List<Express> byUserPhone = ExpressService.findAllAmongYearByPhone(u.getUserPhone());
            LazyBoardUser lu = new LazyBoardUser(u.getUserPhone(), u.getUserName(), byUserPhone.size());
            result.add(lu);
        }

        Collections.sort(result);

        Message msg = new Message();
        if (!result.isEmpty()){
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(result);
        }else{
            msg.setStatus(-1);
            msg.setResult("查询失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/lazyboardMonth.do")
    public String lazyboardMonth(HttpServletRequest request, HttpServletResponse response){
        List<User> userList = UserService.findAll(false, 0, 0);
        List<LazyBoardUser> result = new ArrayList<>();

        for (User u : userList){
            List<Express> byUserPhone = ExpressService.findAllAmongMonthByPhone(u.getUserPhone());
            LazyBoardUser lu = new LazyBoardUser(u.getUserPhone(), u.getUserName(), byUserPhone.size());
            result.add(lu);
        }

        Collections.sort(result);

        Message msg = new Message();
        if (!result.isEmpty()){
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(result);
        }else{
            msg.setStatus(-1);
            msg.setResult("查询失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
