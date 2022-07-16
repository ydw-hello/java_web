package com.ydw.servlet;

import com.ydw.bean.Fruit;
import com.ydw.dao.FruitDao;
import com.ydw.dao.FruitDaoImpl;
import com.ydw.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-13 15:21
 */
@WebServlet("/fruit")
public class FruitServlet extends ViewBaseServlet {
    private FruitDaoImpl fruitDao = new FruitDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // oper关键字，用来判断辨别请求的行为
        String oper = req.getParameter("oper");

        if (StringUtils.isEmpty(oper)) {
            oper = "index";
        }

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.equals(oper)){
                try {
                    method.invoke(this,req,resp);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("oper值非法");
        /*switch (oper) {
            case "index":
                index(req, resp);
                break;
            case "add":
                add(req,resp);
                break;
            case "edit":
                edit(req,resp);
                break;
            case "update":
                update(req,resp);
                break;
            case "delete":
                delete(req,resp);
                break;
            default:
                throw new RuntimeException("oper的值非法");
        }*/
    }


    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String keyword = null;
        String oper = req.getParameter("operation");
        String pageNoStr = req.getParameter("pageNo");
        FruitDao fruitDao = new FruitDaoImpl();
        HttpSession session = req.getSession();
        Integer pageNo = 1;

        if (StringUtils.isNotEmpty(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }
        // 判断是否通过搜索请求
        if (StringUtils.isNotEmpty(oper) && oper.equals("search")) {
            keyword = req.getParameter("keyword");
            if (keyword == null) {
                keyword = "";
            }
            // 如果是通过查询来的，那页码应该还原为1
            pageNo = 1;
            // 将关键字回显到页面
            session.setAttribute("keyword", keyword);
        } else {
            keyword = (String) session.getAttribute("keyword");
            if (keyword == null) {
                keyword = "";
            }
        }

        long totalCount = fruitDao.getTotalCount(keyword);
        long totalPage = (totalCount + 3 - 1) / 3;

        session.setAttribute("totalPage", totalPage);
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageNo > totalPage) {
            pageNo = Math.toIntExact(totalPage);
        }
        session.setAttribute("pageNo", pageNo);

        List<Fruit> fruitList = fruitDao.getPageFruitList(pageNo, keyword);
        session.setAttribute("fruits", fruitList);

        // 通过模板引擎渲染
        processTemplate("index", req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String count = req.getParameter("count");
        String remark = req.getParameter("remark");

        Fruit fruit = new Fruit();

        if (StringUtils.isNotEmpty(name)) {
            fruit.setName(name);
        }
        if (StringUtils.isNotEmpty(price)) {
            fruit.setPrice(Double.parseDouble(price));
        }
        if (StringUtils.isNotEmpty(count)) {
            fruit.setCount(Integer.parseInt(count));
        }
        if (StringUtils.isNotEmpty(remark)) {
            fruit.setRemark(remark);
        }
        fruitDao.add(fruit);

        resp.sendRedirect("fruit");
    }

    protected void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Fruit fruit = fruitDao.getFruitById(id);
        req.setAttribute("fruit",fruit);
        processTemplate("edit",req,resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String count = req.getParameter("count");
        String remark = req.getParameter("remark");

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

        req.getRequestDispatcher("fruit").forward(req,resp);
    }


    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if(StringUtils.isNotEmpty(idStr)){
            int id = Integer.parseInt(idStr);
            fruitDao.delFruitById(id);
        }
        req.getRequestDispatcher("index").forward(req,resp);
    }
}
