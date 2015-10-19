package com.pgs.taxidriver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pgs.taxidriver.dao.UserRoleDAO;
import com.pgs.taxidriver.model.Role;
import com.pgs.taxidriver.model.UserRole;

/**
 * Created by kklonowski on 2015-09-22.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleDAO userRoleDAO;

    @Transactional
    @Override
    public void deleteUserRole(UserRole userRole) {
        userRoleDAO.delete(userRole);
    }

    @Override
    @Transactional
    public List<UserRole> findRoleBySelectedUser(Long id) {
        return userRoleDAO.findRoleBySelectedUser(id);
    }
}
