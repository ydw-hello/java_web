package com.ydw.servlet;

import com.ydw.bean.Fruit;
import com.ydw.dao.FruitDao;
import com.ydw.dao.FruitDaoImpl;
import com.ydw.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-12 13:30
 */
// 使用注解方式注册servlet，等同于在xml中配置(Servlet从3.0开始支持注解注册)
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        FruitDaoImpl fruitDao = new FruitDaoImpl();
        HttpSession session = req.getSession();
        List<Fruit> fruitList = null;
        Long totalCount = 0L;
        String keyword = null;
        Integer pageNo = 1;
        // 判断是否为查询操作
        if (req.getParameter("operation") != null&&req.getParameter("operation").equals("search")) {
            keyword = req.getParameter("keyword");
            // 将pageNp还原为1
            pageNo = 1;
            totalCount = fruitDao.getTotalCount(keyword);
        } else {
            totalCount = fruitDao.getTotalCount();

        }

        // 计算总页数
        long totalPage = (totalCount + 3 - 1) / 3;



        // 将当前页信息，放入session中，带到页面

        session.setAttribute("pageNo", pageNo);
        session.setAttribute("totalPage", totalPage);

        // 判断是否为查询操作
        if (req.getParameter("operation") != null) {
            fruitList = fruitDao.getPageFruitList(pageNo,keyword);
            session.setAttribute("fruits",fruitList);
        } else {
            fruitList = fruitDao.getPageFruitList(pageNo);
            session.setAttribute("fruits", fruitList);
        }

        // 通过ViewBaseServlet，解析模板，渲染数据并返回
        // 进过解析后的物理视图名称为 /   + index   + .html  ，即/index.html;index也称之为逻辑视图平常
        processTemplate("index", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String keyword = null;
        String oper = req.getParameter("operation");
        String pageNoStr = req.getParameter("pageNo");
        FruitDao fruitDao = new FruitDaoImpl();
        HttpSession session = req.getSession();
        Integer pageNo = 1;

        if (StringUtils.isNotEmpty(pageNoStr)){
            pageNo = Integer.parseInt(pageNoStr);
        }
        // 判断是否通过搜索请求
        if (StringUtils.isNotEmpty(oper)&&oper.equals("search")){
            keyword = req.getParameter("keyword");
            if (keyword==null){
                keyword = "";
            }
            // 如果是通过查询来的，那页码应该还原为1
            pageNo = 1;
            // 将关键字回显到页面
            session.setAttribute("keyword",keyword);
        }else{
            keyword= (String) session.getAttribute("keyword");
            if(keyword==null){
                keyword = "";
            }
        }

        long totalCount = fruitDao.getTotalCount(keyword);
        long totalPage = (totalCount+3-1)/3;

        session.setAttribute("totalPage",totalPage);
        if (pageNo<1){
            pageNo = 1;
        }
        if (pageNo>totalPage){
            pageNo = Math.toIntExact(totalPage);
        }
        session.setAttribute("pageNo",pageNo);

        List<Fruit> fruitList = fruitDao.getPageFruitList(pageNo,keyword);
        session.setAttribute("fruits",fruitList);

        // 通过模板引擎渲染
        processTemplate("index",req,resp);
    }
}
