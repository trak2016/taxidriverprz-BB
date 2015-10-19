package com.pgs.taxidriver.service;

import com.pgs.taxidriver.exception.CompanyNotFound;
import com.pgs.taxidriver.model.Company;

import java.util.List;

/**
 * Created by kklonowski on 2015-08-31.
 */
public interface CompanyService {

    /**
     * Get Company List
     *
     * @return Set - Company list
     */
    List<Company> getAll();

    /**
     * Add Company
     *
     * @param company
     */
    void addCompany(Company company);

    /**
     * Delete Company
     *
     * @param
     */
    // void deleteCompany(Long id);
    void deleteCompany(Company company);

    /**
     * Update Company
     *
     * @param company
     */
    void updateCompany(Company company);

    /**
     * @param id
     * @return Company -searching Company
     */
    Company getCompanyById(long id) throws CompanyNotFound;

    /**
     * @param login
     * @return
     */
    List<Company> getCompaniesByLoggedUser(String login);
}
