package com.pgs.taxidriver.init;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * Responsible for customizing servlets
 * Created by kklonowski on 2015-09-01.
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements
        WebApplicationInitializer {
    /**
     * @param servletContext
     * @throws ServletException
     */
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebApplicationConfig.class);
        servletContext.addListener(new ContextLoaderListener(ctx));
        servletContext.setInitParameter("primefaces.THEME", "start");
        servletContext.addListener(new RequestContextListener());
        ctx.setServletContext(servletContext);

        Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
                new DispatcherServlet(ctx));
        servlet.setInitParameter("dispatchOptionsRequest", "true");
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        ctx.refresh();
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebApplicationConfig.class};
    }
}
