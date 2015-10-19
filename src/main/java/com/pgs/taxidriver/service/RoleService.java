package com.pgs.taxidriver.service;

import com.pgs.taxidriver.model.Role;

import java.util.List;


/**
 * Created by dstrek on 2015-09-15.
 */
public interface RoleService {


    Role getRoleById(long id);
    List<Role> findAll();
}