package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.SAXException;

import javax.faces.context.FacesContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;

/**
 * Created by kklonowski on 2015-09-04.
 */
@Component("navRule")
@Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NavigationRule implements Serializable {

    @Autowired
    UserService userService;

    @Autowired
    MapManagedBean mapManagedBean;

    /**************************** ADMIN**************************************/

    public String moveToCompanyList() {
        return "/pages/admin/companyList?faces-redirect=true";
    }

    public String moveToUserList() {
        return "/pages/admin/userList?faces-redirect=true";
    }


    /**************************** OWNER**************************************/

    public String moveToOwnerCompanies() {
        return "/pages/owner/companies?faces-redirect=true";
    }

    public String moveToOwnerEmployeesList() {
        return "/pages/owner/employeesList?faces-redirect=true";
    }

    public String moveToOwnerCompanyCabsList() {
        return "/pages/owner/carList?faces-redirect=true";
    }

    /**************************** DISPATCHER**************************************/

    public String moveMap() throws URISyntaxException, ParserConfigurationException, SAXException, IOException {
        mapManagedBean.reset();
        return "/pages/dispatcher/map?faces-redirect=true";
    }

    public String moveToCourseList() {
        return "/pages/dispatcher/courseList?faces-redirect=true";
    }

    /************************** OTHER **************************************/
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        SecurityContextHolder.clearContext();
        return "/pages/index?faces-redirect=true";
    }

    public User loggedUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return userService.findByLogin(login);
    }

}