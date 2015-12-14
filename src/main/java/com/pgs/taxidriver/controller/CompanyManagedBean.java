package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kklonowski on 2015-08-31.
 */

@Setter
@Getter
@Component("companyMB")
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CompanyManagedBean {

    private static final  Logger LOGGER = LoggerFactory.getLogger(CompanyManagedBean.class);

    @Autowired
    private CompanyService companyService;

    private Company company;

    private Company selectedCompany;

    @PostConstruct
    void init() {
        company = new Company();
    }

    public List<Company> getCompanyList() {
        List<Company> companyList = new ArrayList<Company>();
        try {
            companyList = companyService.getAll();
        } catch (Exception e) {
            LOGGER.error("Error while getting list of all companies!" + e);
        }
        return companyList;
    }

    public List<Company> getCompaniesByLoggedUser() {
        List<Company> listOfComapnies = new ArrayList<Company>();
        try {
            listOfComapnies = companyService.getCompaniesByLoggedUser(
                    SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (Exception e) {
            LOGGER.error("Error while getting companies by logged user : " + SecurityContextHolder.getContext().getAuthentication().getName()
                    + "." + e);
        }
        if (listOfComapnies.get(0) != null) {
            return listOfComapnies;
        } else {
            return listOfComapnies = new ArrayList<Company>();
        }
    }

    public void addCompany() {
        try {
            companyService.addCompany(company);
            reset();
        } catch (Exception e) {
            LOGGER.error("Error while adding new company (" + company.getName() + ")." + e);
        }

    }

    public void updateCompany() {
        try {
            companyService.updateCompany(selectedCompany);
            resetSelected();
        } catch (Exception e) {
            LOGGER.error("Error while updating company (" + selectedCompany.getName() + ")." + e);
        }
    }

    public void deleteCompany() {
        try {
            selectedCompany.setStatus(false);
            companyService.updateCompany(selectedCompany);
            resetSelected();
        } catch (Exception e) {
            LOGGER.error("Error while deleting company (" + selectedCompany.getName() + ")." + e);
        }

    }

    public void reset() {
        this.company.setName("");
        this.company.setPhone("");
        this.company.setAddress("");
    }

    public void resetSelected() {
        selectedCompany = null;
    }

}
