package by.alex.listener;

import by.alex.controller.WagonController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
@Slf4j
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("by.alex");
        context.refresh();
        log.info("Created context");
        WagonController wagonServlet = context.getBean(WagonController.class);
        ServletContext servletContext = sce.getServletContext();

        servletContext.addServlet("wagons", wagonServlet)
                .addMapping("/wagons/*");
    }
}
