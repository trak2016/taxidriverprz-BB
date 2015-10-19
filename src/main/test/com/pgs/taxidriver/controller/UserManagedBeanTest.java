package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.init.WebApplicationConfig;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by jpadjasek on 2015-09-15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
public class UserManagedBeanTest {

    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCRUD() throws Exception {

        try {
            User user = new User();
            user.setName("Janusz");
            user.setPassword("pass");
            user.setLastName("Bzyk");
            user.setDob(new Date());
            user.setPhone("88888888");
            userService.addUser(user);

            List<User> list = userService.getUsers();
            Collections.sort(list, (o1, o2) -> o1.getId().compareTo(o2.getId()));

            Assert.assertEquals("Janusz", list.get(list.size() - 1).getName());
            Assert.assertEquals("Bzyk", list.get(list.size() - 1).getLastName());
            Assert.assertEquals("pass", list.get(list.size() - 1).getPassword());
            Assert.assertEquals("88888888", list.get(list.size() - 1).getPhone());

            user.setName("TEST");
            user.setPassword("TEST");
            user.setDob(new Date());
            user.setPhone("7777");
            userService.updateUser(user);

            Assert.assertEquals("TEST", list.get(list.size() - 1).getName());
            Assert.assertEquals("TEST", list.get(list.size() - 1).getPassword());
            Assert.assertEquals("7777", list.get(list.size() - 1).getPhone());

            userService.deleteUser(user);

            Assert.assertNull(userService.getUserById(user.getId()));

        } finally {

        }
    }
}
