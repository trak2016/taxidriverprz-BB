package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Car;
import com.pgs.taxidriver.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.round;

/**
 * Created by akrawczyk on 2015-09-07.
 */
@Service
public class CarDAOImpl extends GenericDAOImpl<Car> implements CarDAO {

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    @Override
    @Transactional
    public List<Car> getCarsForAllUserCompanies(User user) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        List<Car> cars =
                session.createQuery(
                        "Select c from Car c LEFT JOIN c.company cp LEFT JOIN cp.owners uc WHERE uc.user =?")
                        .setParameter(0, user)
                        .list();
        return cars;
    }

    /**
     * SELECT * FROM CAR c LEFT JOIN company cp ON cp.id = c.company_id LEFT JOIN user_company uc ON uc.company_id = cp.id WHERE uc.user_id = 1
     * get car statistic (incomes and distance traveled)
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public List<Double> getCarStat(long userId) {

        // declare variables
        long dayInMilis = 1000 * 60 * 60 * 24;
        List<Double> list = new ArrayList<Double>();
        List<Object[]> sums = new ArrayList<Object[]>();
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();

        // year stats
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 365 * dayInMilis);
        addToList(getStatsFromSpecificDate(session, calendar, userId), list);

        // previous month stats
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 30 * dayInMilis);
        addToList(getStatsFromSpecificDate(session, calendar, userId), list);

        // last week stats
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 7 * dayInMilis);
        addToList(getStatsFromSpecificDate(session, calendar, userId), list);

        // today stats
        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        addToList(getStatsFromSpecificDate(session, calendar, userId), list);

        // add maximum value to last element
        list.add(list.size(), Collections.max(list));

        return list;
    }

    /**
     * \
     * Get sum of distance and cost from specific date until today
     *
     * @param session  opened hibernate session
     * @param calendar calendar with set date
     * @param userId   id of user (car driver)
     * @return List with two double values, first one is sum of cost, second one is sum of distance
     */
    private List<Object[]> getStatsFromSpecificDate(Session session, Calendar calendar, long userId) {
        Query query =
                session.createQuery("select round(sum(uc.cost)*100)/100, round(100*sum(uc.distance))/100 from Car c join c.driver u join u.courses uc where uc.date > :year and u.id=:userid  group by c.id");
        query.setParameter("year", calendar.getTime());
        query.setParameter("userid", userId);
        return query.list();
    }

    /**
     * Append element returned from query to list
     *
     * @param queryResult list which query results
     * @param statList    list which current stats
     * @return
     */
    private void addToList(List<Object[]> queryResult, List<Double> statList) {
        int cell = statList.size();

        if (!queryResult.isEmpty()) {
            round((Double) queryResult.get(0)[0]);
            statList.add(cell, (Double) queryResult.get(0)[0]);
            statList.add(cell + 1, (Double) queryResult.get(0)[1]);
        } else {
            statList.add(cell, 0.0);
            statList.add(cell + 1, 0.0);
        }
    }

    /**
     * Get car from dataBase by plateNumber
     *
     * @param plateNumber
     * @return a car with this plateNumber
     */
    @Override
    public Car getCarByPlateNumer(String plateNumber) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        return (Car) session.createQuery("from Car c where c.plateNumber=?").setParameter(0, plateNumber).list().get(0);
    }

    @Override
    public void addCar(Car car) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        session.merge(car);
    }

    @Override
    public Car getCarByUserId(Long id) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        return (Car) session.createQuery("from Car c where c.driver.id=?").setParameter(0, id).list().get(0);
    }
}
