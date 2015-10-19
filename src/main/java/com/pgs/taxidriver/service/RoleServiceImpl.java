package com.pgs.taxidriver.service;

import com.pgs.taxidriver.dao.RoleDAO;
import com.pgs.taxidriver.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dstrek on 2015-09-15.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDAO roleDAO;


    @Transactional
     public Role getRoleById(long id){
        Role role = roleDAO.getById(Role.class, id);
        return role;
    }

    @Transactional
    public List<Role> findAll(){
        List<Role> roleList = roleDAO.findAll();
        return roleList;

    }

}
