package com.ydw.controller;

import com.ydw.utils.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 通过配置文件和反射实现 路径 -> 相应的类 -> 相应的方法
 * @author ydw
 * @create 2022-07-13 17:36
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private Map<String,Object> map = new HashMap<>();
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // 解析配置文件
            InputStream in = DispatcherServlet.class.getClassLoader().getResourceAsStream("applicationContext.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(in);
            NodeList nodeList = document.getElementsByTagName("bean");
            for(int i=0;i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                if (node.getNodeType()==Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    String path = element.getAttribute("id");
                    String clazzName = element.getAttribute("class");
                    Class<?> clazz = Class.forName(clazzName);
                    Object obj = clazz.newInstance();
                    map.put(path,obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 1.获取路径
        String servletPath = req.getServletPath();
        // 去掉/
        servletPath = servletPath.substring(1);
        // 去掉.do
        int index = servletPath.lastIndexOf(".do");
        String path = servletPath.substring(0,index);

        // 2.根据路径，找到相应的类的实例
        Object instance = map.get(path);
        // 3.调用相应的方法
        // oper关键字，用来判断辨别请求的行为
        String oper = req.getParameter("oper");

        if (StringUtils.isEmpty(oper)) {
            oper = "index";
        }

        try {
//            Method method = instance.getClass().getDeclaredMethod(oper, HttpServletRequest.class);
            Method[] methods = instance.getClass().getDeclaredMethods();
            for(Method method:methods){
                if (method.getName().equals(oper)) {
                    method.setAccessible(true);
                    Parameter[] parameters = method.getParameters();
                    Object[] objects = new Object[parameters.length];
                    for(int i=0;i<parameters.length;i++){
                        Parameter parameter = parameters[i];
                        // java8新特性，通过设置-parameters获取方法参数的名称
                        String parameterName = parameter.getName();
                        if ("request".equals(parameterName)) {
                            objects[i] = req;
                        }else if ("response".equals(parameterName)){
                            objects[i] = resp;
                        }else{
                            String value = req.getParameter(parameterName);
                            objects[i] = value;
                            if (value != null) {
                                if(parameter.getType().getName().equals("java.lang.Integer")){
                                    objects[i] = Integer.parseInt(value);
                                }else if (parameter.getType().getName().equals("java.lang.Double")){
                                    objects[i] = Double.parseDouble(value);
                                }
                            }
                        }
                    }
                    Object returnValue = method.invoke(instance, objects);
                    String returnStr = (String) returnValue;
                    // 表示要重定向
                    if (returnStr.startsWith("redirect:")){
                        String redirectPath = returnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectPath);
                    }else{ // 表示要渲染
                        super.processTemplate(returnStr,req,resp);
                    }
                    return;
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("oper值非法");

    }
}
