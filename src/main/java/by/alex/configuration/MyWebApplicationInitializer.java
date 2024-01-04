//package by.alex.configuration;
//
//
//import by.alex.service.WagonService;
//import by.alex.service.impl.WagonServiceImpl;
//import by.alex.util.print.PrintInfo;
//import com.google.gson.Gson;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebListener;
//import javax.servlet.http.HttpServlet;
//
//@WebListener
//public class MyWebApplicationInitializer implements ServletContextListener {
//
//    public WagonService wagonService;
//    public Gson gson;
//    public PrintInfo printInfo;
//
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//
//        HttpServlet servletContext = null;
//        try {
//            servletContext = sce.getServletContext().createServlet(HttpServlet.class);
//        } catch (ServletException e) {
//            throw new RuntimeException(e);
//        }
//        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//        wagonService = context.getBean(WagonServiceImpl.class);
//        gson=context.getBean(Gson.class);
//        printInfo=context.getBean(PrintInfo.class);
//
//    }
//}
