package com.ydw.servlet;

import com.ydw.dao.FruitDaoImpl;
import com.ydw.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ydw
 * @create 2022-07-13 0:13
 */
@WebServlet("/del")
public class DelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idStr = req.getParameter("id");
        if(StringUtils.isNotEmpty(idStr)){
            int id = Integer.parseInt(idStr);
            FruitDaoImpl fruitDao = new FruitDaoImpl();
            fruitDao.delFruitById(id);
        }
        req.getRequestDispatcher("index").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
