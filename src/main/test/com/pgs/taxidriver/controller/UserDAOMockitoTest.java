package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.dao.UserDAO;
import com.pgs.taxidriver.exception.UserNotFound;
import com.pgs.taxidriver.init.WebApplicationConfig;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.service.UserService;
import com.pgs.taxidriver.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * Created by akrawczyk on 2015-09-03.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
public class UserDAOMockitoTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUser() {
        //given
        final User u = createUser();
        //then
        userService.addUser(u);
        //result
        Mockito.verify(userDAO, Mockito.times(1)).create(u);
    }

    private static User createUser() {
        User user = new User();
        user.setName("Alina");
        user.setLastName("Kownacka");
        Date date = new Date();
        user.setDob(date);
        user.setPhone("123456789");
        return user;
    }

    @Test
    public void testDeleteUser() throws Exception {

        when(userDAO.getById(User.class, 0l)).thenReturn(new User());

        userService.deleteUser(anyObject());

        Mockito.verify(userDAO, Mockito.times(1)).delete(anyObject());
    }

    @Test
    public void testFindUserById() throws UserNotFound {
        //given
        final Long id = 123l;
        Mockito.doReturn(createSimpleUser()).when(userDAO).getById(User.class, id);
        //when
        final User result = userService.getUserById(id);
        //then
        assertNotNull(result);
    }

    private static User createSimpleUser() {
        return new User();
    }

    @Test
    public void testGetAll() {
        //given
        List<User> expectedUserList = new ArrayList<User>();
        expectedUserList.add(new User("Ann", "Smith", new Date(), "123456789"));
        expectedUserList.add(new User("John", "Novak", new Date(), "123456789"));
        expectedUserList.add(new User("Fitzgerald", "Pope", new Date(), "123456789"));
        //when
        when(userService.getUsers()).thenReturn(expectedUserList);
        List<User> actualUserList = userDAO.findAll();
        //then
        assertNotNull(actualUserList);
        assertSame(expectedUserList, actualUserList);
    }

    @Test
    public void testUpdateUserData()
    {
        User user = new User("Aniela", "Panieñska", new Date(), "987654321");
        User changedUserData =  new User(user.getId(), "Aniela", "Anielska", new Date(), "123456789");
        userService.updateUser(changedUserData);
        Mockito.verify(userDAO, Mockito.times(1)).update(changedUserData);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userDAO).update(userArgumentCaptor.capture());
        User updatedUser = userArgumentCaptor.getValue();
        assertEquals(changedUserData, updatedUser);
    }

    @Test
    public void testFindUserByLogin()
    {
        final String login = "login";
        Mockito.doReturn(new User()).when(userDAO).findByLogin(login);
        //when
        final User result = userService.findByLogin(login);
        //then
        assertNotNull(result);

    }

    @Test
    public void getEmployeesByCompany() {
        //given
        final Long id = 123l;
        //then
        userService.getEmployeesByCompany(id);
        //result
        Mockito.verify(userDAO, Mockito.times(1)).getEmployeesByCompany(id);
    }

    @Test
    public void getCarsByCompany() {
        //given
        final Long id = 123l;
        //then
        userService.getCarsByCompany(id);
        //result
        Mockito.verify(userDAO, Mockito.times(1)).getCarsByCompany(id);
    }

}