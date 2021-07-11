package com.kaikeba.wx.controller;

import com.kaikeba.bean.*;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.mvc.ResponseView;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author JJH
 * @2021/7/11 17:15
 * 说明：
 */
public class QRCodeController {
    @ResponseView("/wx/createQRCode.do")
    public String createQrcode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        // 通过快递列表点进来是express，通过主页点进来是user
        String type = request.getParameter("type");
        String userPhone = null;
        String qrCodeContent = null;
        if ("express".equals(type)){
            // 快递二维码
            //code
            qrCodeContent = "express_" + code;
        }else {
            // 用户二维码
            // userPhone
            Object temp = UserUtil.getWxUser(request.getSession());
            if (temp instanceof User){
                User wxUser = (User) temp;
                userPhone = wxUser.getUserPhone();
            }else if (temp instanceof Courier){
                Courier wxUser = (Courier) temp;
                userPhone = wxUser.getCourierPhone();
            }
            qrCodeContent = "userPhone_" + userPhone;
        }
        HttpSession session = request.getSession();
        session.setAttribute("qrcode",qrCodeContent);
        return "/personQRcode.html";
    }

    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String qrcode = (String) session.getAttribute("qrcode");
        Message msg = new Message();
        if (qrcode == null){
            msg.setStatus(-1);
            msg.setResult("取件码获取出错，请用户重新操作");
        }else {
            msg.setStatus(0);
            msg.setResult(qrcode);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/updateStatus.do")
    public String updateExpressStatus(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        boolean flag = ExpressService.updateStatus(code);
        Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("取件成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("取件码不存在，请更新二维码");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");

        Express e = ExpressService.findByCode(code);
        BootStrapTableExpress e2 = null;
        if (e != null){
            String inTime = DateFormatUtil.format(e.getInTime());
            String status = "待取件";
            e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,null,status,e.getSysPhone());
        }

        Message msg = new Message();
        if (e == null){
            msg.setStatus(-1);
            msg.setResult("取件码不存在");
        }else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
