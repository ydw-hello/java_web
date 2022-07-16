package com.ydw.servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ydw
 * @create 2022-07-12 13:49
 */
public class ViewBaseServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    // 配置模板解析器对象，并在模板引擎中设置
    @Override
    public void init() throws ServletException {
        // 1.获取servletContext对象
        ServletContext servletContext = getServletContext();

        // 2.创建thymeleaf解析器对象
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(servletContext);

        // 3.给解析器对象设置参数
        // ①HTML是默认模式，明确设置是为了更好的理解
        templateResolver.setTemplateMode(TemplateMode.HTML);

        // ②设置前缀
        String prefix = servletContext.getInitParameter("view-prefix");
        templateResolver.setPrefix(prefix);

        // ③设置后缀
        String suffix = servletContext.getInitParameter("view-suffix");
        templateResolver.setSuffix(suffix);

        // ④设置缓存过期时间（毫秒）
        templateResolver.setCacheTTLMs(60000L);

        // ⑤设置是否缓存
        templateResolver.setCacheable(true);

        // ⑥设置服务器端编码方式
        templateResolver.setCharacterEncoding("utf-8");

        // 4.创建模板引擎对象
        templateEngine = new TemplateEngine();

        // 5.给模板引擎对象设置模板解析器
        templateEngine.setTemplateResolver(templateResolver);
    }

    /**
     * 这个方法是用来让子类解析模板的（用来完成数据的渲染和资源的转发）
     * @param templateName 传入的模板的逻辑视图名称
     * @param request
     * @param response
     */
    protected void processTemplate(String templateName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.设置响应体内容的类型和字符集
        response.setContentType("text/html;charset=UTF-8");
        // 2.创建webContext对象
        WebContext webContext = new WebContext(request,response,getServletContext());
        // 3.处理模板数据
        templateEngine.process(templateName,webContext,response.getWriter());
    }
}
