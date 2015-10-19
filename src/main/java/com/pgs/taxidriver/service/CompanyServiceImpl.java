package com.pgs.taxidriver.service;

import com.pgs.taxidriver.dao.CompanyDAO;
import com.pgs.taxidriver.exception.CompanyNotFound;
import com.pgs.taxidriver.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kklonowski on 2015-08-31.
 */
@Transactional(readOnly = true)
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDAO companyDAO;

    /**
     * Get Company List
     *
     * @return Set - Company list
     */
    @Override
    @Transactional
    public List<Company> getAll() {
        return companyDAO.findAll();
    }

    /**
     * Add new Company
     *
     * @param company
     */
    @Override
    @Transactional
    public void addCompany(Company company) {
        companyDAO.create(company);
    }

    /**
     * Update Company
     *
     * @param company
     */
    @Override
    @Transactional
    public void updateCompany(Company company) {
        companyDAO.update(company);
    }

    /**
     * Delete Company
     *
     * @param company
     */
    @Override
    @Transactional
    public void deleteCompany(Company company) {
        companyDAO.delete(company);
    }

    /**
     * @param id
     * @return Company -searching Company
     */
    @Override
    @Transactional
    public Company getCompanyById(long id) throws CompanyNotFound {
        Company company = companyDAO.getById(Company.class, id);
        return company;
    }

    @Override
    public List<Company> getCompaniesByLoggedUser(String login) {
        return companyDAO.getCompaniesByLoggedUser(login);
    }
}
