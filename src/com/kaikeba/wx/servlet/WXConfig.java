package com.kaikeba.wx.servlet;

import com.kaikeba.wx.util.SignatureUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author JJH
 * @2021/7/11 17:23
 * 说明：
 */

@WebServlet("/wxconf.do")
public class WXConfig extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        String urlText = request.getParameter("xurl");
        try {
            String json = SignatureUtil.getConfig(urlText ).toJSON();
            pw.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.close();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
