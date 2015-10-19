package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.User;

import java.util.List;

/**
 * Created by jpadjasek on 2015-08-31.
 */
public interface UserDAO extends GenericDAO<User> {

    User findByLogin(String login);

    List<User> getEmployeesByCompany(Long id);

    /**
     * @param id company
     * @return list of cars chosen company
     */
    List<Car> getCarsByCompany(Long id);
    List<User> findAll();
}
