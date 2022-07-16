package com.ydw.servlet;

import com.ydw.bean.Fruit;
import com.ydw.dao.FruitDao;
import com.ydw.dao.FruitDaoImpl;
import com.ydw.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ydw
 * @create 2022-07-12 21:16
 */
@WebServlet("/update")
public class UpdateServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String count = req.getParameter("count");
        String remark = req.getParameter("remark");

        FruitDao fruitDao = new FruitDaoImpl();
        Fruit fruit = fruitDao.getFruitById(id);

        // 更新数据
        if(StringUtils.isNotEmpty(name)){
            fruit.setName(name);
        }
        if (StringUtils.isNotEmpty(price)){
            fruit.setPrice(Double.parseDouble(price));
        }
        if (StringUtils.isNotEmpty(count)){
            fruit.setCount(Integer.parseInt(count));
        }
        if (StringUtils.isNotEmpty(remark)){
            fruit.setRemark(remark);
        }

        fruitDao.updateFruit(fruit);

        req.getRequestDispatcher("index").forward(req,resp);
    }
}
