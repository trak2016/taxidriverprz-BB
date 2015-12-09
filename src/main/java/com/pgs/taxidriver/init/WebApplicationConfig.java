package com.pgs.taxidriver.init;

import com.pgs.taxidriver.websocket.WebSocketConfig;
import com.pgs.taxidriver.model.*;
import com.pgs.taxidriver.model.Role;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

/**
 * Class responsible for configuring our application, set data source properties, set folders where to look for resources etc.
 * Created by kklonowski on 2015-09-01.
 */
@Configuration
@EnableTransactionManagement
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.pgs.taxidriver"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = Configuration.class)})
@Import({SecurityConfig.class, WebSocketConfig.class})
public class WebApplicationConfig extends WebMvcConfigurerAdapter {

    @Resource
    private Environment env;

    /**
     * Set location where to look for resources e.g. scripts, images, css files.
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/resources/");
    }

    /**
     * Set default page
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/pages/").setViewName("index.xhtml");
        registry.addViewController("/login").setViewName("login");
    }

    /**
     * Configure flyway
     *
     * @return
     */
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

        return dataSource;
    }

    // set as default servlet
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public HibernateTemplate hibernateTemplate() {
        return new HibernateTemplate(sessionFactory());
    }

    @Bean
    @DependsOn("flyway")
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource())
                .addAnnotatedClasses(Company.class)
                .addAnnotatedClasses(Car.class)
                .addAnnotatedClasses(User.class)
                .addAnnotatedClasses(Course.class)
                .addAnnotatedClasses(UserCompany.class)
                .addAnnotatedClasses(UserRole.class)
                .addAnnotatedClasses(Role.class)
                .buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager hibTransMan() {
        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();

        // set data source when database will exist
        flyway.setDataSource(dataSource());

        // migartion can be called on non-empty databse
        flyway.setBaselineOnMigrate(true);

        // folder to look for migrations, this setting will look for it in resources/dbMigration folder
        flyway.setLocations("dbMigration");

        return flyway;
    }

}
