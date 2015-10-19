package com.pgs.taxidriver.dao;

import java.util.List;

/**
 * Created by kklonowski on 2015-09-03.
 */
public interface GenericDAO<T> {

    /**
     * Generic method to find all objects persisted in database
     *
     * @return
     */
    List<T> findAll();

    /**
     * Generic method to persist new object
     *
     * @param t
     * @return
     */
    T create(T t);

    /**
     * Generic method to update object
     *
     * @param t
     * @return
     */
    T update(T t);

    /**
     * Generic method to delete object
     *
     * @param t
     */
    void delete(Object t);

    T getById(Class clazz, Object t);
}
