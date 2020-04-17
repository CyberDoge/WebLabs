package com.web.app.config;

import com.web.app.dbServices.DBService;
import org.apache.catalina.filters.CorsFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.EnumSet;

@WebListener
public class MainContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FilterRegistration.Dynamic fr = sce.getServletContext()
                .addFilter("tomcatCorsFilter", CorsFilter.class);
        EnumSet<DispatcherType> disps = EnumSet.of(
                DispatcherType.REQUEST, DispatcherType.FORWARD);
        fr.addMappingForUrlPatterns(disps, false, "/*");
        fr.setInitParameter(CorsFilter.PARAM_CORS_ALLOWED_ORIGINS, "http://localhost:3000");

        DBService dbService = new DBService();
//        dbService.runChangeLog();

        sce.getServletContext().setAttribute("dbService", dbService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBService dbService = (DBService) sce.getServletContext().getAttribute("dbService");
        dbService.close();
    }
}