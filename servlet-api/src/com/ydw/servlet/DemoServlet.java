package com.ydw.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author ydw
 * @create 2022-07-14 1:12
 */
@WebServlet(urlPatterns = {"/demo"},initParams = {
        @WebInitParam(name = "年后",value = "今天")
})
public class DemoServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        String location = servletContext.getInitParameter("contextConfigLocation");
        System.out.println(location);
    }


}
