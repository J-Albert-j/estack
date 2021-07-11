package com.kaikeba.controller;
//7.编写controller与前端进行交互
//8.将该controller在配置文件中添
//9.编写对应前端


import com.kaikeba.bean.*;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.service.UserService;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.RandomUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author JJH
 * @2021/7/3 15:24
 * 说明：
 */
public class CourierController {

    @ResponseBody("/courier/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Integer>> data = CourierService.console();
        Message msg = new Message();
        if (data.size() == 0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
//    @ResponseBody("/courier/list.do")
//    public String list(HttpServletRequest request, HttpServletResponse response){
//        // 获取查询数据的起始索引值
//        int offset = Integer.parseInt(request.getParameter("offset"));
//        // 获取当前页要查询的数据量
//        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
//        // 查询
//        List<User> list = UserService.findAll(true, offset, pageNumber);
//        List<BootStrapTableUser> list2 = new ArrayList<>();
//        for(User u : list){
//            String enrollTime = DateFormatUtil.format(u.getEnrollTime());
//            String loginTime = u.getLoginTime()==null?"未登录":DateFormatUtil.format(u.getLoginTime());
//            BootStrapTableUser u2 = new BootStrapTableUser(u.getId(),u.getUserName(),"******",u.getUserIdNumber(),u.getUserPhone(),enrollTime,loginTime);
//            list2.add(u2);
//        }
//        List<Map<String, Integer>> console = UserService.console();
//        Integer total = console.get(0).get("data_size");
//        // 将集合封装为 bootstrap-table识别的格式
//        ResultData<BootStrapTableUser> data = new ResultData<>();
//        data.setRows(list2);
//        data.setTotal(total);
//        String json = JSONUtil.toJSON(data);
//        return json;
//    }
    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response){
        int offset = Integer.parseInt(request.getParameter("offset"));
        // 获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        List<Courier> list = CourierService.findAll(true, offset, pageNumber);
        List<BootStrapTableCourier> list2 = new ArrayList<>();
        for (Courier c : list){
            String enrollTime = DateFormatUtil.format(c.getEnrollTime());
            String loginTime = c.getLoginTime()==null?"未登录":DateFormatUtil.format(c.getLoginTime());
            String sendNumber = null;
            int courierSendNumber = c.getCourierSendNumber();
            if (courierSendNumber <= 10000){
                sendNumber = courierSendNumber + "";
            }else{
                sendNumber = courierSendNumber/10000 + "w+";
            }
            BootStrapTableCourier c2 = new BootStrapTableCourier(c.getId(),c.getCourierName(),c.getCourierPhone(),"******",c.getCourierPassword(),sendNumber,enrollTime,loginTime);
            list2.add(c2);
        }
        List<Map<String, Integer>> console = CourierService.console();
        Integer total = console.get(0).get("data_size");
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String courierName = request.getParameter("courierName");
        String courierPhone = request.getParameter("courierPhone");
        String courierIdNumber = request.getParameter("courierIdNumber");
        String courierPassword = request.getParameter("courierPassword");
        Courier c = new Courier(courierName,courierPhone,courierIdNumber,courierPassword);
        boolean flag = CourierService.insert(c);
        Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("快递员录入成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("快递员录入失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response){
        String courierPhone = request.getParameter("courierPhone");
        Courier c = CourierService.findByPhone(courierPhone);
        Message msg = new Message();

        if (c == null){
            msg.setStatus(-1);
            msg.setResult("手机号不存在");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(c);
        }

        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String courierName = request.getParameter("courierName");
        String courierPhone = request.getParameter("courierPhone");
        String courierIdNumber = request.getParameter("courierIdNumber");
        String courierPassword = request.getParameter("courierPassword");

        Courier newCourier = new Courier(courierName,courierPhone,courierIdNumber,courierPassword);
        Message msg = new Message();
        boolean flag = CourierService.update(id, newCourier);
        if (flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = CourierService.delete(id);
        Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("删除成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
