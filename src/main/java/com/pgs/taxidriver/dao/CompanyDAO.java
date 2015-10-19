package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Company;

import java.util.List;

/**
 * Created by kklonowski on 2015-08-31.
 */
public interface CompanyDAO extends GenericDAO<Company> {

    List<Company> getCompaniesByLoggedUser(String login);

}
