package com.ydw.servlet;

import com.ydw.bean.Fruit;
import com.ydw.dao.FruitDao;
import com.ydw.dao.FruitDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ydw
 * @create 2022-07-12 20:25
 */
@WebServlet("/edit")
public class EditServlet extends ViewBaseServlet {
    private FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Fruit fruit = fruitDao.getFruitById(id);
        req.setAttribute("fruit",fruit);
        processTemplate("edit",req,resp);
    }
}
