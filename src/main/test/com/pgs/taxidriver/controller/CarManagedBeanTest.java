package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.dao.CarDAO;
import com.pgs.taxidriver.dao.CompanyDAO;
import com.pgs.taxidriver.dao.UserDAO;
import com.pgs.taxidriver.init.WebApplicationConfig;
import com.pgs.taxidriver.model.*;
import com.pgs.taxidriver.service.CarService;
import com.pgs.taxidriver.service.CarServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hsadecki on 2015-09-08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CarManagedBeanTest {

    @Autowired
    private CarService carService = new CarServiceImpl();

    @Autowired
    private CarDAO carDAO;

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private UserDAO userDAO;

    @Before
    public void init() {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCarStat() throws Exception {

        long dayInMilis = 1000*60*60*24;

        List<Double> expectedStatList = new ArrayList<Double>();
        //save courses to database through company create
        expectedStatList.add(new Double(220.0));
        expectedStatList.add(new Double(31.3));

        expectedStatList.add(new Double(167.5));
        expectedStatList.add(new Double(22.6));

        expectedStatList.add(new Double(67.5));
        expectedStatList.add(new Double(13.6));

        expectedStatList.add(new Double(22.5));
        expectedStatList.add(new Double(3.6));

        expectedStatList.add(new Double(220.0));

        //save to database data and later delete them
        Calendar calendar = Calendar.getInstance();
        //create company
        Company company = new Company("", "PGSSoftware", "0700880774", "TestCompany");

        //owner of company
        Set<User> listUser = new HashSet<User>();
        calendar.set(1965, 11, 26);
        User ownerUser = new User("Szef", "Firmy", calendar.getTime(), "123");
        listUser.add(ownerUser);
        calendar.set(1990, 05, 23);
        User user1 = new User("Pracownik", "1", calendar.getTime(), "1");
        listUser.add(user1);
        calendar.set(1991, 00, 14);
        User user2 = new User("Pracownik", "2", calendar.getTime(), "2");
        listUser.add(user2);

        //add cars TODO
        calendar.set(1994, 01, 01);
        Car car = new Car("RJA 1234", "BMW x5", calendar.getTime(), true, "300 kg", (short) 7, company, user1, 14.2, 15.2);
        Set<Car> carList = new HashSet<Car>();
        carList.add(car);
        user1.setCars(carList);
        company.setCars(carList);

        Set<UserCompany> listUserCompany = new HashSet<UserCompany>();
        UserCompany userCompany = new UserCompany(company, ownerUser);
        listUserCompany.add(userCompany);
        UserCompany userCompany1 = new UserCompany(company, user1);
        listUserCompany.add(userCompany1);
        UserCompany userCompany2 = new UserCompany(company, user2);
        listUserCompany.add(userCompany2);


        company.setOwners(listUserCompany);

        //create courses and add to list
        Set<Course> coursesList = new HashSet<Course>();

        //add courses for user 1
        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), 01, 01, 12, 15, 00);
        Course course = new Course(52.50f, 8.7f, user1, calendar.getTime());
        coursesList.add(course);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 25 * dayInMilis);
        course = new Course(100.0f, 9.0f, user1, calendar.getTime());
        coursesList.add(course);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 3 * dayInMilis);
        course = new Course(12.5f, 2.0f, user1, calendar.getTime());
        coursesList.add(course);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis());
        course = new Course(22.5f, 3.6f, user1, calendar.getTime());
        coursesList.add(course);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 2 * dayInMilis);
        course = new Course(32.5f, 8.0f, user1, calendar.getTime());
        coursesList.add(course);

        user1.setCourses(coursesList);

        userDAO.create(ownerUser);
        userDAO.create(user1);
        userDAO.create(user2);

        companyDAO.create(company);
        carDAO.create(car);

        Car carT = new Car();
        carT.setId(car.getId());
        User user = new User();
        user.setId(user1.getId());
        carT.setDriver(user);

        //WHEN
        List<Double> resultList = carService.getCarStats(carT);


        //DELETE DATA FROM DATABASE
        carDAO.delete(car);

        companyDAO.delete(company);

        userDAO.delete(ownerUser);
        userDAO.delete(user1);
        userDAO.delete(user2);

        //THEN
        assertNotNull(resultList);
        assertEquals(expectedStatList, resultList);

    }

    // test CRUD ... someday..

}
