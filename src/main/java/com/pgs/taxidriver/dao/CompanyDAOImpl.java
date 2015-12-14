package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Company;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kklonowski on 2015-08-31.
 */
@Service
public class CompanyDAOImpl extends GenericDAOImpl<Company> implements CompanyDAO {

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    @Override
    public List<Company> findAll() {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Query query = session.createQuery("SELECT c FROM Company c where c.status=true order by name");
        return query.list();

    }

    @Override
    public List<Company> getCompaniesByLoggedUser(String login) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        return session
                .createQuery(
                        "SELECT c from Company c right join c.owners uc right join uc.user u where u.login=? and c.status=true")
                .setParameter(0, login)
                .list();
    }

}
