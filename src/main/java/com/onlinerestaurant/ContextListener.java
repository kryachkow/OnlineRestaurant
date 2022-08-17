package com.onlinerestaurant;

import com.onlinerestaurant.db.DBUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

import org.apache.log4j.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);
        String localesFileName = context.getInitParameter("locales");
        String localesFileRealPath = context.getRealPath(localesFileName);
        Properties locales = new Properties();
        try {
            locales.load(Files.newInputStream(Paths.get(localesFileRealPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.setAttribute("locales", locales);
        DBUtils.getInstance();
    }

}
