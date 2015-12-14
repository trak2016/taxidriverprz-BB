package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.model.Company;
import com.pgs.taxidriver.model.Course;
import com.pgs.taxidriver.model.User;
import com.pgs.taxidriver.model.UserRole;
import com.pgs.taxidriver.service.CompanyService;
import com.pgs.taxidriver.service.CourseService;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by hsadecki on 2015-09-07.
 */

@Setter
@Getter
@Component("courseMB")
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CourseManagedBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseManagedBean.class);

    @Autowired
    private CourseService courseService;

    @Autowired
    private NavigationRule navigationRule;

    private String companyId;

    private Course selectedCourse;

    private List<Course> courseList;

    private List<Company> companyList;

    private Company company;

    private User user;

    private UserRole userRole;

    @Autowired
    private CompanyService companyService;

    public void onStartup() {
        user = navigationRule.loggedUser();
        List<UserRole> userRoles = user.getRoles();
        if (userRoles.size() == 1 && userRoles.get(0).getRole().getName().equals("ROLE_DISPATCHER")) {
            this.company = getCompaniesByLoggedUser().get(0);
            this.companyId = String.valueOf(company.getId());
            this.courseList = getCoursesByCompany();
        }
    }

    @PostConstruct
    public void init() {
        this.selectedCourse = null;
        this.courseList = new ArrayList<Course>();
        this.companyList = new ArrayList<Company>();
        this.companyId = "";
    }

    public void reset() {
        this.selectedCourse = null;
        this.courseList = new ArrayList<Course>();
        this.companyList = new ArrayList<Company>();
        this.companyId = "";
    }

    public List<Company> getCompaniesByLoggedUser() {
        this.companyList = new ArrayList<Company>();
        try {
            this.companyList = companyService.getCompaniesByLoggedUser(
                    SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (Exception e) {
            LOGGER.error("Error while getting companies by logged user: " + SecurityContextHolder.getContext().getAuthentication().getName()
                    + "." + e);
        }

        if (this.companyList.isEmpty() || this.companyList == null || this.companyList.get(0) == null) {
            this.companyList = new ArrayList<Company>();
        }
        if (this.companyList.get(0) != null) {
            return this.companyList;
        } else {
            return this.companyList = new ArrayList<Company>();
        }
    }

    public String dataFormater(Date data) throws ParseException {
        String dataWithTime = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int h = calendar.get(Calendar.HOUR);
        int m = calendar.get(Calendar.MINUTE);
        String hh = Integer.toString(h);
        String mm = Integer.toString(m);
        if (h < 10) {
            hh = "0" + h;
        }
        if (m < 10) {
            mm = "0" + m;
        }
        dataWithTime = day + "/" + month + "/" + year + " " + hh + ":" + mm;

        return dataWithTime;
    }

    public List<Course> getCoursesByCompany() {
        if (!companyId.equals("") && companyId != null) {
            this.courseList = courseService.getAllSortedByCompany(Long.parseLong(companyId));
        } else {
            this.courseList = new ArrayList<Course>();
        }
        return this.courseList;
    }

    public void setCompanyId(String companyId) {
        if (companyId != null && !companyId.equals("")) {
            this.companyId = companyId;
        }
        else{
            this.companyId = "";
        }
    }

}
