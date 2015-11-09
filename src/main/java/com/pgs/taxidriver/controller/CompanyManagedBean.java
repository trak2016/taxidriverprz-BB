package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component("companyMB")
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CompanyManagedBean {

    private final static Logger logger = LoggerFactory.getLogger(CompanyManagedBean.class);

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
            e.printStackTrace();
        }
        return companyList;
    }

    public List<Company> getCompaniesByLoggedUser() {
        List<Company> listOfComapnies = new ArrayList<Company>();
        try {
            listOfComapnies = companyService.getCompaniesByLoggedUser(
                    SecurityContextHolder.getContext().getAuthentication().getName().toString());
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

    }

    public void updateCompany() {
        try {
            companyService.updateCompany(selectedCompany);
            // selectedCompany = null;
            resetSelected();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCompany() {
        try {
            companyService.deleteCompany(selectedCompany);
            // selectedCompany = null;
            resetSelected();
        } catch (Exception e) {
            e.printStackTrace();
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

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
