package com.ydw.servlet;

import com.ydw.bean.Fruit;
import com.ydw.dao.FruitDao;
import com.ydw.dao.FruitDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-10 0:08
 */
public class MyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FruitDaoImpl fruitDao = new FruitDaoImpl();
        List<Fruit> fruitList = fruitDao.getFruitList();
        fruitList.forEach(System.out::println);

        HttpSession session = req.getSession();
        session.setAttribute("fruits",fruitList);
        resp.sendRedirect("show");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
