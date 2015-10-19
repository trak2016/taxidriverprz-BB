package com.pgs.taxidriver.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kklonowski on 2015-09-03.
 */
public class GenericDAOImpl<T> implements GenericDAO<T> {

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    private Class<T> type;

    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Transactional
    @Override
    public List<T> findAll() {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        return crit.list();
    }

    @Transactional
    public T create(T t) {
        return (T) this.hibernateTemplate.save(t);
    }

    @Transactional
    public void delete(Object t) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        session.delete(t);
    }

    @Override
    public T update(T t) {
        return this.hibernateTemplate.merge(t);
    }

    @Override
    public T getById(Class clazz, Object t) {
        Object obj = null;
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();

        try {
            obj = session.get(clazz, (Serializable) t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (obj == null) {
            return null;
        } else {
            return (T) obj;
        }

    }
}
