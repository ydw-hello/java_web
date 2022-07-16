package com.ydw.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ydw
 * @create 2022-07-03 14:02
 */
public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 获取表单信息

        String characterEncoding = req.getCharacterEncoding();
        System.out.println(characterEncoding);
        String fname = req.getParameter("fname");
        String fprice = req.getParameter("fprice");
        String fnumber = req.getParameter("num");


        double price = Double.parseDouble(fprice);
        int num = Integer.parseInt(fnumber);

        System.out.println("你好，北京");
        System.out.println("名称："+fname+",价格："+price+"数量："+num);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
