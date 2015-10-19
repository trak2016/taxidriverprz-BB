package com.pgs.taxidriver.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by jpadjasek on 2015-08-31.
 */
@Entity
// (name = "Usr")
@Table(name = "usr")
public class User {
    @Id
    private Long id;
    private String name;
    private String lastName;
    private Date dob;
    private String phone;
    private Set<Car> cars;
    private Set<Course> courses;
    private Set<UserCompany> ownedCompanies;
    private List<UserRole> roles;
    private String password;
    private String login;
    private Boolean active;

    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "usr_id_seq", name = "usr_id_seq")
    @GeneratedValue(generator = "usr_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    @Basic
    @Column(name = "dob")
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Basic
    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String name, String lastname, Date dob, String phone) {
        this.lastName = lastname;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
    }

    public User(Long id, String name, String lastname, Date dob, String phone) {
        this.id = id;
        this.lastName = lastname;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
    }

    public User(String login, String password, List<UserRole> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null)
            return false;
        if (dob != null ? !dob.equals(user.dob) : user.dob != null)
            return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "driver")
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<UserCompany> getOwnedCompanies() {
        return ownedCompanies;
    }

    public void setOwnedCompanies(Set<UserCompany> ownedCompanies) {
        this.ownedCompanies = ownedCompanies;
    }


    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
