package com.kaikeba.wx.controller;

import com.kaikeba.bean.*;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author JJH
 * @2021/7/11 16:37
 * 说明：
 */
public class ExpressController {

    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findByUserPhone(HttpServletRequest request, HttpServletResponse response){
        Object temp = UserUtil.getWxUser(request.getSession());
        String userPhone="";
        if (temp instanceof User){
            User wxUser = (User) temp;
            userPhone = wxUser.getUserPhone();
        }else if (temp instanceof Courier){
            Courier wxUser = (Courier) temp;
            userPhone = wxUser.getCourierPhone();
        }
        System.out.println(temp);
        System.out.println(userPhone);
        List<Express> list = ExpressService.findByUserPhone(userPhone);
        // 转变为更好显示的格式
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e:list){
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件":"已取件";
            String code = e.getCode()==null ? "已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }

        Message msg = new Message();
        if (list.size() == 0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
            // 只保留未取件的快递，并按照入库时间排序
            Stream<BootStrapTableExpress> status0Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("待取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1,o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int)(o1Time - o2Time);
            });

            // 只保留已取件的快递，并按照入库时间排序
            Stream<BootStrapTableExpress> status1Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("已取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1,o2) -> {
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int)(o1Time - o2Time);
            });
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            Map data = new HashMap<>();
            data.put("status0",s0);
            data.put("status1",s1);
            msg.setData(data);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        System.out.println(userPhone);
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        System.out.println(list);
        // 转变为更好显示的格式
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e:list){
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件":"已取件";
            String code = e.getCode()==null ? "已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        Message msg = new Message();
        if (list.size() == 0){
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(list2);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/addExpress.do")
    public String addExpress(HttpServletRequest request, HttpServletResponse response){
        String expressNumber = request.getParameter("expressNumber");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String company = request.getParameter("company");
        String sysPhone = ((Courier)UserUtil.getWxUser(request.getSession())).getCourierPhone();
        Message msg = new Message();
        Express e = ExpressService.findByNumber(expressNumber);
        if (e != null){
            // 单号重复
            msg.setStatus(-1);
            msg.setResult("快递单号重复");
        }else {
            Express e2 = new Express(expressNumber,username,userPhone,company,sysPhone);
            boolean flag = ExpressService.insert(e2);
            if (flag){
                // 快递插入成功
                msg.setStatus(0);
                msg.setResult("快递插入成功");
            }else {
                // 快递插入失败
                msg.setStatus(-1);
                msg.setResult("快递插入失败");
            }
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/searchExpress.do")
    public String searchExpress(HttpServletRequest request, HttpServletResponse response){
        String expressNum = request.getParameter("expressNum");
        Object wxUser = UserUtil.getWxUser(request.getSession());
        String userPhone = "";
        Message msg = new Message();
        if (wxUser instanceof User){
            userPhone = ((User) wxUser).getUserPhone();
        }else if (wxUser instanceof Courier){
            userPhone = ((Courier) wxUser).getCourierPhone();
        }

        Express express = ExpressService.findByNumber(expressNum);
        System.out.println(express);
        if (express != null && express.getUserPhone().equals(userPhone)){
            HttpSession session = request.getSession();
            session.setAttribute("searchExpress",express);
        }
        msg.setStatus(0);
        msg.setResult("查询成功");
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/findSearchExpress.do")
    public String findSearchExpress(HttpServletRequest request, HttpServletResponse response){
        Express e = (Express)request.getSession().getAttribute("searchExpress");
        Message msg = new Message();
        if (e != null){
            int status = e.getStatus();
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String statusStr = status == 0 ? "待取件":"已取件";
            String code = e.getCode()==null ? "已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,statusStr,e.getSysPhone());
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }else {
            msg.setStatus(-1);
            msg.setResult("查询失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
