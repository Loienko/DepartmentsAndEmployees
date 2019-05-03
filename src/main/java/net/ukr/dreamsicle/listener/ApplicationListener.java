package net.ukr.dreamsicle.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;

@WebListener
public class ApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        ServiceManager.getInstance(servletContextEvent.getServletContext());
        LOGGER.info("APP STARTED " + new Date());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        ServiceManager.getInstance(servletContextEvent.getServletContext()).destroy();
        LOGGER.info("APP DESTROYED");
    }
}
