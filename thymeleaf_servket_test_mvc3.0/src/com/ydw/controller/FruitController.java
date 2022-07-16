package com.ydw.controller;

import com.ydw.bean.Fruit;
import com.ydw.dao.FruitDao;
import com.ydw.dao.FruitDaoImpl;
import com.ydw.utils.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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

public class FruitController{
    private FruitDaoImpl fruitDao = new FruitDaoImpl();


    private String index(String operation,Integer pageNo,String keyword,HttpServletRequest request) {


        FruitDao fruitDao = new FruitDaoImpl();
        HttpSession session = request.getSession();


        if (pageNo==null){
            pageNo = 1;
        }
        // 判断是否通过搜索请求
        if (StringUtils.isNotEmpty(operation) && operation.equals("search")) {
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

        if (pageNo > totalPage) {
            pageNo = Math.toIntExact(totalPage);
        }
        if (pageNo < 1) {
            pageNo = 1;
        }
        session.setAttribute("pageNo", pageNo);

        List<Fruit> fruitList = fruitDao.getPageFruitList(pageNo, keyword);
        session.setAttribute("fruits", fruitList);

        // 通过模板引擎渲染
//        processTemplate("index", req, resp);
        return "index";
    }

    private String add(String name,Double price,Integer count,String remark) {
//        String name = req.getParameter("name");
//        String price = req.getParameter("price");
//        String count = req.getParameter("count");
//        String remark = req.getParameter("remark");

        Fruit fruit = new Fruit();

        if (StringUtils.isNotEmpty(name)){
            fruit.setName(name);
        }
        if (price!=null){
            fruit.setPrice(price);
        }
        if (count!=null){
            fruit.setCount(count);
        }
        if (StringUtils.isNotEmpty(remark)){
            fruit.setRemark(remark);
        }
        fruitDao.add(fruit);

//        resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    protected String edit(Integer id,HttpServletRequest request){
        Fruit fruit = fruitDao.getFruitById(id);
        request.setAttribute("fruit",fruit);
//        processTemplate("edit",req,resp);
        return "edit";
    }

    private String update(Integer id,String name,Double price,Integer count,String remark) {

        Fruit fruit = fruitDao.getFruitById(id);

        // 更新数据
        if(StringUtils.isNotEmpty(name)){
            fruit.setName(name);
        }
        if (price!=null){
            fruit.setPrice(price);
        }
        if (count!=null){
            fruit.setCount(count);
        }
        if (StringUtils.isNotEmpty(remark)){
            fruit.setRemark(remark);
        }

        fruitDao.updateFruit(fruit);

//        resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }


    private String delete(Integer id)  {

        if(id!=null){
            fruitDao.delFruitById(id);
        }
//        resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

}
