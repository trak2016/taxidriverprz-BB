package com.pgs.taxidriver.service;

import com.pgs.taxidriver.model.Role;
import com.pgs.taxidriver.model.UserRole;

import java.util.List;

/**
 * Created by kklonowski on 2015-09-22.
 */
public interface UserRoleService {

    /**
     *
     * @param userRole
     */
    void deleteUserRole(UserRole userRole);

    /**
     *
     * @param id
     * @return
     */
    List<UserRole> findRoleBySelectedUser(Long id);
}