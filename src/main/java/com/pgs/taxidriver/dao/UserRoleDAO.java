package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Role;
import com.pgs.taxidriver.model.UserRole;

import java.util.List;

/**
 * Created by kklonowski on 2015-09-22.
 */
public interface UserRoleDAO extends GenericDAO<UserRole>{

    /**
     *
     * @param id
     * @return
     */
    List<UserRole> findRoleBySelectedUser(Long id);
}
