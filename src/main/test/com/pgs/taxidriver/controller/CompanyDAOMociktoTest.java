package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.dao.CompanyDAO;
import com.pgs.taxidriver.exception.CompanyNotFound;
import com.pgs.taxidriver.init.WebApplicationConfig;
import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.model.UserRole;
import com.pgs.taxidriver.service.CompanyService;
import com.pgs.taxidriver.service.CompanyServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * Created by akrawczyk on 2015-09-15.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
public class CompanyDAOMociktoTest {

    @Mock
    private CompanyDAO companyDAO;

    @InjectMocks
    private CompanyService companyService = new CompanyServiceImpl();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUser() {
        //given
        final Company c = createCompany();
        //then
        companyService.addCompany(c);
        //result
        Mockito.verify(companyDAO, Mockito.times(1)).create(c);
    }

    private static Company createCompany() {
        Company company = new Company();
        company.setAddress("Rzesz�w, Morgowa 5");
        company.setPhone("177777777");
        company.setName("Some Company Name");
        return company;
    }

    @Test
    public void testDeleteCompany() throws Exception {

        when(companyDAO.getById(Company.class, 0l)).thenReturn(new Company());

        companyService.deleteCompany(anyObject());

        Mockito.verify(companyDAO, Mockito.times(1)).delete(anyObject());
    }

    @Test
    public void testFindCompanyById() throws CompanyNotFound{
        //given
        final Long id = 123l;
        Mockito.doReturn(new Company()).when(companyDAO).getById(Company.class, id);
        //when
        final Company result = companyService.getCompanyById(id);
        //then
        assertNotNull(result);
    }

    @Test
    public void testGetAll() {
        //given
        List<Company> expectedCompanyList = new ArrayList<Company>();
        expectedCompanyList.add(new Company("logo1.jpg", "Letnia 7", "123456789", "Company 1"));
        expectedCompanyList.add(new Company("logo2.jpg", "Jesienna 7", "123456789", "Company 2"));
        expectedCompanyList.add(new Company("logo3.jpg", "Zimowa 7", "123456789", "Company 3"));
        //when
        when(companyService.getAll()).thenReturn(expectedCompanyList);
        List<Company> actualCompanyList = companyDAO.findAll();
        //then
        assertNotNull(actualCompanyList);
        assertSame(expectedCompanyList, actualCompanyList);
    }

    @Test
    public void testUpdateUserData()
    {
        Company company = (new Company("logo1.jpg", "Letnia 7", "123456789", "Company 1"));
        Company changedCompanyData =  new Company(company.getId(), "logo2.jpg", "Jesienna 7", "123456789", "Company 2",true);
        companyService.updateCompany(changedCompanyData);
        Mockito.verify(companyDAO, Mockito.times(1)).update(changedCompanyData);
        ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        Mockito.verify(companyDAO).update(companyArgumentCaptor.capture());
        Company updatedCompany = companyArgumentCaptor.getValue();
        assertEquals(changedCompanyData, updatedCompany);
    }

    @Test
    public void testGetCompaniesByLoggedUser() {
        //given
        final User user = new User("login", "pasword", new ArrayList<UserRole>());
        //then
        companyService.getCompaniesByLoggedUser(user.getLogin());
        //result
        Mockito.verify(companyDAO, Mockito.times(1)).getCompaniesByLoggedUser(user.getLogin());
    }



}
