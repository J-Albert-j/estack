package com.kaikeba.wx.filter;

import com.kaikeba.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author JJH
 * @2021/7/11 17:19
 * 说明：
 */
@WebFilter({"/index.html"})
public class UserFilter {

    //@Override
    public void destroy() {
    }

   // @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        Object wxUser = UserUtil.getWxUser(session);
        if (wxUser != null){
            chain.doFilter(req, resp);
        }else {
            ((HttpServletResponse)resp).sendRedirect("login.html");
        }

    }

   // @Override
    public void init(FilterConfig config) throws ServletException {

    }
}
