package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.model.*;
import com.pgs.taxidriver.service.CompanyService;
import com.pgs.taxidriver.service.RoleService;
import com.pgs.taxidriver.service.UserRoleService;
import com.pgs.taxidriver.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jpadjasek on 2015-09-01.
 */


@Setter
@Getter
@Component("userMB")
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserManagedBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagedBean.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    NavigationRule navigationRule;

    List<User> userList;

    Set<UserRole> userRoles;

    private User user;

    private User selectedUser;

    private Car car;

    private User activeUser;

    private String oldPassword;

    private String newPassword;

    private String newPasswordRepeated;


    private Set<Role> roleSet;

    String companyId = "";

    private Company selectedCompany;

    Role selectedRole;

    private List<User> listEmpl;

    public void setActiveUser() {
        this.activeUser = navigationRule.loggedUser();

    }

    @PostConstruct
    void init() {
        user = new User();
    }

    /**
     * function for the calendar
     */
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    /**
     * function for the calendar
     */
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }

    /**
     * reset fields ind the xhtml form
     */
    public void reset() {
        this.user.setLogin("");
        this.user.setName("");
        this.user.setLastName("");
        this.user.setDob(null);
        this.user.setPhone("");
        this.user.setPassword("");
        this.selectedUser = null;

    }

    /**
     * add user to the database
     */
    public void addUser() {
        try {
            user.setLogin(user.getName() + user.getLastName());
            user.setPassword("password");
            userService.addUser(user);
            reset();
        } catch (DataAccessException e) {
            LOGGER.error("Error while adding new user." + e);
        }
    }

    /**
     * update user in the database
     */
    public void addNewEmployee() {

        List<UserRole> roles = new ArrayList<UserRole>();
        for (Role r : roleSet) {
            UserRole userRole = new UserRole();
            userRole.setRole(r);
            userRole.setUser(user);
            roles.add(userRole);
        }


        Set<UserCompany> companies = new HashSet<UserCompany>();
        UserCompany userCompany = new UserCompany();
        userCompany.setCompany(selectedCompany);
        userCompany.setUser(user);
        companies.add(userCompany);


        user.setOwnedCompanies(companies);
        user.setRoles(roles);
        user.setLogin(user.getName() + user.getLastName());
        user.setPassword(hashing("password"));
        userService.addUser(user);
        reset();

    }

    @Transactional
    public void updateUser() {
        try {
            List<UserRole> roles = new ArrayList<UserRole>();
            for (Role r : roleSet) {
                UserRole userRole = new UserRole();
                userRole.setRole(r);
                userRole.setUser(selectedUser);
                roles.add(userRole);
            }
            selectedUser.setRoles(roles);
            userService.updateUser(selectedUser);
            selectedUser = null;
        } catch (Exception e) {
            LOGGER.error("Error while updating user: " + selectedUser.getName() + " " + selectedUser.getLastName() + " - " + selectedUser.getLogin() + "." + e);
        }
    }

    public List<User> getUserList() {
        List<User> userList = new ArrayList<User>();
        try {
            userList = userService.getUsers();
        } catch (Exception e) {
            LOGGER.error("Error while getting all users list!" + e);
        }
        return userList;
    }

    public List<User> getEmployeesByCompany() {
        if (companyId != null && !companyId.equals("")) {
            this.listEmpl = userService.getEmployeesByCompany(Long.parseLong(companyId));

            User loggedUser =
                    userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName().toString());
            this.listEmpl.remove(loggedUser);
        } else {
            this.listEmpl = new ArrayList<User>();
        }
        return this.listEmpl;
    }

    /**
     * delete user from the data base
     */
    public void deleteUser() {
        try {
            selectedUser.setActive(false);
            userService.updateUser(selectedUser);
            selectedUser = null;
        } catch (Exception e) {
            LOGGER.error("Error while deleting user: " + selectedUser.getName() + " " + selectedUser.getLastName() +
                    " (" + selectedUser.getLogin() + ")." + e);
        }
    }


    public void deleteUserRole(UserRole userRole) {
        try {
            userRoleService.deleteUserRole(userRole);
        } catch (Exception e) {
            LOGGER.error("Error while deleting user role: " + userRole.getRole() + "." + e);
        }
    }

    /**
     * @param data
     * @return data in expected format
     * @throws ParseException
     */
    public String dataFormater(Date data) throws ParseException {

        String dataWithoutTime = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dataWithoutTime = day + "/" + month + "/" + year;

        return dataWithoutTime;
    }

    /**
     * @param password
     * @return hashing password by MD5
     */
    public static String hashing(String password) {

        String md5 = null;

        try {

            // Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            // Update input string in message digest
            digest.update(password.getBytes(), 0, password.length());

            // Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            LOGGER.error("Error while hashing password." + e);
        }
        return md5;
    }

    /**
     * @return company list logged user.
     */
    public List<Company> getUserCompanyList() {
        List<Company> userCompanies = new ArrayList<Company>();
        try {

            String login = navigationRule.loggedUser().getLogin();
            userCompanies = companyService.getCompaniesByLoggedUser(login);
        } catch (Exception e) {
            LOGGER.error("Error while getting user (" + navigationRule.loggedUser().getName() + " " + navigationRule.loggedUser().getLastName() + " - " +
                    navigationRule.loggedUser().getLogin() + ")companies." + e);
        }
        if (userCompanies.get(0) != null) {
            return userCompanies;
        } else {
            return userCompanies = new ArrayList<Company>();
        }
    }

    /**
     * @return return list of cars chosen company
     */
    public List<Car> getCarsByCompany() {
        List<Car> cars = new ArrayList<Car>();
        if (companyId != "") {
            cars = userService.getCarsByCompany(Long.parseLong(companyId));
            companyId = "";
        }
        return cars;
    }


    public List<Role> findAllRoles() {
        List<UserRole> userRolesTemp = navigationRule.loggedUser().getRoles();

        if ((userRolesTemp.stream().filter(x -> Objects.equals(x.getRole().getName(), "ROLE_ADMIN")).collect(Collectors.toList())).size() != 0) {
            return roleService.findAll();
        } else {
            List<Role> allRoles = roleService.findAll();
            allRoles.removeIf(x -> Objects.equals(x.getName(), "ROLE_ADMIN"));

            return allRoles;
        }


    }

    public List<Role> findOtherRoles(Long id) {
        List<Role> allRoles = findAllRoles();

        List<UserRole> userRolesTemp = userRoleService.findRoleBySelectedUser(id);
        List<Role> rolesTemp = userRolesTemp.stream().map(x -> x.getRole()).collect(Collectors.toList());
        allRoles.removeAll(rolesTemp);

        return allRoles;
    }

    public Set<UserRole> findRoleBySelectedUser(Long id) {
        List<UserRole> temp = userRoleService.findRoleBySelectedUser(id);
        this.userRoles = new HashSet<UserRole>(temp);
        if (this.userRoles != null && !this.userRoles.equals("")) {
            getEmployeesByCompany();
            return this.userRoles;
        } else {
            return this.userRoles = new HashSet<UserRole>();
        }
    }

    /**
     * Method for editing logged user profile data.
     */
    public void editProfile() {
        try {
            userService.updateUser(activeUser);
        } catch (Exception e) {
            LOGGER.error("Error while updating user profile: " + activeUser.getName() + " " + activeUser.getLastName() + " (" + activeUser.getLogin() + ")." + e);
        }
    }

    public void chnageUserPassword() {
        boolean valid = false;
        if (Objects.equals(oldPassword, activeUser.getPassword()) && Objects.equals(newPassword, newPasswordRepeated) && !newPassword.isEmpty() && !newPasswordRepeated.isEmpty()) {

            activeUser.setPassword(newPasswordRepeated);
            userService.updateUser(activeUser);
            RequestContext context = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your password was changed successfully!"));
            RequestContext.getCurrentInstance().update("acceptedMessage");
            oldPassword = "";
            newPassword = "";
            newPasswordRepeated = "";
            LOGGER.info("Password for user " + activeUser.getName() + " " + activeUser.getLastName() + " (" + activeUser.getLogin() + ") was change successfully.");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error while trying change password. Probably old password is wrong!"));
            RequestContext.getCurrentInstance().update("refusedMessage");
            LOGGER.error("Error while trying change password. Probably old password is wrong!");
        }

    }
}
