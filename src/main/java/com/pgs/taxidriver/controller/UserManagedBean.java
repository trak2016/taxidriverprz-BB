package com.pgs.taxidriver.controller;

import com.pgs.taxidriver.model.*;
import com.pgs.taxidriver.service.CompanyService;
import com.pgs.taxidriver.service.RoleService;
import com.pgs.taxidriver.service.UserRoleService;
import com.pgs.taxidriver.service.UserService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
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
@Component("userMB")
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserManagedBean {

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

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    Set<UserRole> userRoles;

    private User user;

    private User selectedUser;

    private Car car;

    private User activeUser;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeated() {
        return newPasswordRepeated;
    }

    public void setNewPasswordRepeated(String newPasswordRepeat) {
        this.newPasswordRepeated = newPasswordRepeat;
    }

    private String oldPassword;

    private String newPassword;

    private String newPasswordRepeated;


    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    private Set<Role> roleSet;

    public Company getSelectedCompany() {
        return selectedCompany;
    }


    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    String companyId = "";
    private Company selectedCompany;


    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    // String roleId = "";
    Role selectedRole;

    /**
     * getters and setters
     */


    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser() {
        this.activeUser = navigationRule.loggedUser();

    }

    @PostConstruct
    void init() {
        user = new User();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public List<User> getUserList() {
        List<User> userList = new ArrayList<User>();
        try {
            userList = userService.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
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

    public List<User> getListEmpl() {
        return listEmpl;
    }

    public void setListEmpl(List<User> listEmpl) {
        this.listEmpl = listEmpl;
    }

    private List<User> listEmpl;

    /**
     * delete user from the data base
     */
    public void deleteUser() {
        try {
            selectedUser.setActive(false);
            userService.updateUser(selectedUser);
            selectedUser = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteUserRole(UserRole userRole) {
        try {
            userRoleService.deleteUserRole(userRole);
        } catch (Exception e) {
            e.printStackTrace();
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

            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void chnageUserPassword() {
        boolean valid = false;
        if (Objects.equals(oldPassword, activeUser.getPassword()) && Objects.equals(newPassword, newPasswordRepeated) && !newPassword.isEmpty() && !newPasswordRepeated.isEmpty()) {
            try {
                activeUser.setPassword(newPasswordRepeated);
                userService.updateUser(activeUser);
                RequestContext context = RequestContext.getCurrentInstance();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Your password was changed successfully!"));
                RequestContext.getCurrentInstance().update("acceptedMessage");
                oldPassword = "";
                newPassword = "";
                newPasswordRepeated = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error while trying change password. Probably old password is wrong!"));
            RequestContext.getCurrentInstance().update("refusedMessage");
        }

    }
}
