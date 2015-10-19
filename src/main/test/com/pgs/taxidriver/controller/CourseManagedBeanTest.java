package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.model.Course;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.model.UserCompany;
import com.pgs.taxidriver.service.CourseService;
import com.pgs.taxidriver.service.CourseServiceImpl;
import com.pgs.taxidriver.init.WebApplicationConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertNotNull;

/**
 * Created by akrawczyk on 2015-09-03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
public class CourseManagedBeanTest {

    @Autowired
    private CourseService courseService = new CourseServiceImpl();

    @Before
    public void init() {

    }

    @Before
    public void setUp() {

    }

    @Test
    @Transactional
    public void testGetAllSortedByCompany()
    {
        //GIVEN
        List<Course> expectedCourseList = new ArrayList<Course>();


        Calendar calendar = Calendar.getInstance();

        //users
        calendar.set(2000, 03, 01);
        User user1 = new User("Hubert","Sadecki",calendar.getTime(),"12345");
        calendar.set(1997, 01, 01);
        User user2 = new User("Jan", "Kowalski", calendar.getTime(),"543112");
        calendar.set(1999, 12, 01);
        User user3 = new User("Tomasz", "Nowak", calendar.getTime(),"0700880");

        //company
        Company company = new Company();
        company.setId((long)3);
        company.setAddress("Rzeszow");
        company.setLogo(null);
        company.setName("TaxiDriver");
        company.setPhone("6220000");

        //userCompany
        UserCompany userCompany1 = new UserCompany();
        userCompany1.setUser(user1);
        userCompany1.setCompany(company);
        UserCompany userCompany2 = new UserCompany();
        userCompany2.setUser(user2);
        userCompany2.setCompany(company);
        UserCompany userCompany3 = new UserCompany();
        userCompany3.setUser(user3);
        userCompany3.setCompany(company);


        //set Courses
        calendar.set(2015, 02, 01, 00, 00 ,00);
        Course course1 = new Course(49.21f, 10.01f, user1, calendar.getTime());
        expectedCourseList.add(course1);
        calendar.set(2015, 01, 01, 00, 00, 00);
        Course course2 = new Course(11.9f, 33.2f, user2, calendar.getTime());
        expectedCourseList.add(course2);
        calendar.set(2015, 00, 15, 00, 00, 00);
        Course course3 = new Course(11.0f, 13.23f, user3, calendar.getTime());
        expectedCourseList.add(course3);

        //get data from database
        List<Course> actualCoursesList = courseService.getAllSortedByCompany(3);
        //THEN
        assertNotNull(actualCoursesList);
        assertTrue(expectedCourseList.toString().equals(actualCoursesList.toString()));
    }

    @Test
    public void testDateFormatter(){
        CourseManagedBean courseManagedBean = new CourseManagedBean();
        Date date = new Date();
        date.setTime(1994300000);
        try {
            assertNotNull(date);
            assertEquals(courseManagedBean.dataFormater(date), "24/1/1970 02:58");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}