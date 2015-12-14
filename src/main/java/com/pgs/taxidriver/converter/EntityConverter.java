package com.pgs.taxidriver.converter;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * Created by akrawczyk on 2015-09-09.
 */

@Component("entityConverter")
@SessionScoped
public class EntityConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityConverter.class);

    @Autowired
    private HibernateTemplate hibernateTemplate;

    private Session session;

    @Transactional
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String string) {
        try {
            String[] split = string.split(":");
            this.session = hibernateTemplate.getSessionFactory().getCurrentSession();
            return session.get(Class.forName(split[0]), Long.valueOf(split[1]));
        } catch (NumberFormatException | ClassNotFoundException e) {
            LOGGER.error("An error occured while converting String to an Object", e);
            return null;
        }
    }

    @Transactional
    @Override
    public String getAsString(FacesContext fc, UIComponent component, Object object) {
        try {
            if (object != null) {
                Class<? extends Object> clazz = object.getClass();
                for (Field f : clazz.getDeclaredFields()) {
                    if (f.isAnnotationPresent(Id.class)) {
                        f.setAccessible(true);
                        Long id = (Long) f.get(object);
                        return clazz.getCanonicalName() + ":" + id.toString();
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOGGER.error("Error while getting object as string." + e);
        }
        return null;
    }
}

