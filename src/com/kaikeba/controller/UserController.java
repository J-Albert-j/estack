package com.kaikeba.controller;

import com.kaikeba.bean.BootStrapTableUser;
import com.kaikeba.bean.Message;
import com.kaikeba.bean.ResultData;
import com.kaikeba.bean.User;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.UserService;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JJH
 * @2021/7/2 18:20
 * 说明：
 */
public class UserController {
    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Integer>> data = UserService.console();
        Message msg = new Message();
        if (data.size() == 0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);//msg ：status；data；传给function（data）中的data
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response){
        // 获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        // 获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        // 查询
        List<User> list = UserService.findAll(true, offset, pageNumber);
        List<BootStrapTableUser> list2 = new ArrayList<>();
        for(User u : list){
            String enrollTime = DateFormatUtil.format(u.getEnrollTime());
            String loginTime = u.getLoginTime()==null?"未登录":DateFormatUtil.format(u.getLoginTime());
            BootStrapTableUser u2 = new BootStrapTableUser(u.getId(),u.getUserName(),"******",u.getUserIdNumber(),u.getUserPhone(),enrollTime,loginTime);
            list2.add(u2);
        }
        List<Map<String, Integer>> console = UserService.console();
        Integer total = console.get(0).get("data_size");
        // 将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableUser> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String userIdNumber = request.getParameter("userIdNumber");
        String userPassword = request.getParameter("userPassword");
        User u = new User(userName,userPassword,userIdNumber, userPhone );
        boolean flag = UserService.insert(u);
        Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("用户录入成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("用户录入失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        User u = UserService.findByUserPhone(userPhone);
        Message msg = new Message();
        if (u == null){
            msg.setStatus(-1);
            msg.setResult("手机号不存在");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(u);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String userIdNumber = request.getParameter("userIdNumber");
        String userPassword = request.getParameter("userPassword");
        User newUser = new User(userName,userPassword,userIdNumber,userPhone);
        Message msg = new Message();
        boolean flag = UserService.update(id, newUser);
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

    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = UserService.delete(id);
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
