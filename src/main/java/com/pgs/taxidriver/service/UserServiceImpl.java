package com.pgs.taxidriver.service;

import com.pgs.taxidriver.dao.UserDAO;
import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jpadjasek on 2015-08-31.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    /**
     * Add new user
     *
     * @param user
     */
    @Transactional
    @Override
    public void addUser(User user) {
        userDAO.create(user);
    }

    /**
     * Update user
     *
     * @param user
     */
    @Transactional
    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }

    /**
     * Delete user
     *
     * @param user
     */
    @Transactional
    @Override
    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    /**
     * Finding user by Id
     *
     * @param id
     * @return finded user
     */
    @Transactional
    @Override
    public User getUserById(Long id) {
        User user = userDAO.getById(User.class, id);
        return user;
    }

    /**
     * List of users
     *
     * @return list of users
     */
    @Transactional
    @Override
    public List<User> getUsers() {
        List<User> userList = userDAO.findAll();
        return userList;
    }

    @Override
    public User findByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    public List<User> getEmployeesByCompany(Long id) {
        return userDAO.getEmployeesByCompany(id);
    }

    /**
     * @param id
     * @return list of cars chosen company
     */
    @Override
    public List<Car> getCarsByCompany(Long id) {
        return userDAO.getCarsByCompany(id);
    }

}
