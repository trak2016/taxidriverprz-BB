package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Role;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.pgs.taxidriver.model.UserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kklonowski on 2015-09-22.
 */
@Service
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole> implements UserRoleDAO {

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    @Override
    @Transactional
    public List<UserRole> findRoleBySelectedUser(Long id){
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Query query = session.createQuery("FROM UserRole ur where ur.user.id = ?").setParameter(0, id);
        return query.list();
    }
}
