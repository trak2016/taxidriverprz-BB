package com.pgs.taxidriver.service;

import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.User;

import java.util.List;

/**
 * Created by jpadjasek on 2015-08-31.
 */

public interface UserService {

    /**
     * @param user
     */
    void addUser(User user);

    /**
     * @param user
     */
    void updateUser(User user);

    /**
     * @param id
     * @return user with this id
     */
    User getUserById(Long id);

    /**
     * @return list of users
     */
    List<User> getUsers();

    /**
     * @param user delete user
     */
    void deleteUser(User user);

    /**
     * @param login
     * @return
     */
    User findByLogin(String login);

    List<User> getEmployeesByCompany(Long id);

    /**
     * @param id
     * @return list of cars chosen company
     */
    List<Car> getCarsByCompany(Long id);
}
