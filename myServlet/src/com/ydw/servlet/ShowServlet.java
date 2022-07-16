package com.ydw.servlet;

import com.ydw.bean.Fruit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-12 1:00
 */
public class ShowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Fruit> fruits = (List<Fruit>) req.getSession().getAttribute("fruits");
        System.out.println("==================");
        fruits.forEach(System.out::println);

    }
}
