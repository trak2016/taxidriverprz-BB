package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.init.WebApplicationConfig;
import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.service.CompanyService;
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
import java.util.Comparator;
import java.util.List;

/**
 * Created by kklonowski on 2015-09-03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationConfig.class)
@WebAppConfiguration
@Transactional
public class CompanyManagedBeanTest {

    @Autowired
    CompanyService companyService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCRUD() throws Exception {
        try {
            Company company = new Company();
            company.setName("Testowa");
            company.setAddress("Test Address");
            company.setPhone("123456789");
            companyService.addCompany(company);

            List<Company> list = companyService.getAll();
            Collections.sort(list, new Comparator<Company>() {
                public int compare(Company o1, Company o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });

            Assert.assertEquals("Testowa", list.get(list.size() - 1).getName());
            Assert.assertEquals("Test Address", list.get(list.size() - 1).getAddress());
            Assert.assertEquals("123456789", list.get(list.size() - 1).getPhone());

            company.setName("Zmieniona");
            company.setAddress("Test Address Another");
            company.setPhone("987654321");
            companyService.updateCompany(company);
            Assert.assertEquals("Zmieniona", list.get(list.size() - 1).getName());
            Assert.assertEquals("Test Address Another", list.get(list.size() - 1).getAddress());
            Assert.assertEquals("987654321", list.get(list.size() - 1).getPhone());

            companyService.deleteCompany(company);

            Assert.assertNull(companyService.getCompanyById(company.getId()));

        } finally {

        }

    }
}