package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.exception.CompanyNotFound;
import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.service.CompanyService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akrawczyk on 2015-08-31.
 */
@Service
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

    private final static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    @Autowired
    CompanyService companyService;

    @Override
    @Transactional
    public User findByLogin(String login) {
        List<User> users = new ArrayList<User>();

        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        users = session
                .createQuery("from User where login=?")
                .setParameter(0, login)
                .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public List<User> getEmployeesByCompany(Long id) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        List<User> employees =
                session
                        .createQuery(
                                "Select u from User u LEFT JOIN u.ownedCompanies uc left JOIN uc.company c where c.id=?")
                        .setParameter(0, id)
                        .list();

        return employees;
    }

    /**
     * @param id company
     * @return @return list of cars chosen company
     */
    @Transactional
    @Override
    public List<Car> getCarsByCompany(Long id) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        List<Car> cars = new ArrayList<>();
        try {
            Company company = companyService.getCompanyById(id);

            cars.addAll(company.getCars());
        } catch (CompanyNotFound companyNotFound) {
            logger.error("Error while getting cars by company id: " + id + "." + companyNotFound);
        }

        return cars;
    }

    @Transactional
    @Override
    public List<User> findAll() {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        List<User> users = session
                .createQuery(
                        "Select u from User u  where u.active=true")
                .list();
        return users;
    }

}
