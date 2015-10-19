package com.pgs.taxidriver.model;

import javax.persistence.*;

/**
 * Created by jpadjasek on 2015-08-31.
 */
@Entity
@Table(name = "user_company")
public class UserCompany {
    @Id
    private Long id;
    private Company company;
    private User user;

    public UserCompany() {
    }

    public UserCompany(Company company, User user) {
        this.company = company;
        this.user = user;
    }

    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "user_company_id_seq", name = "user_company_id_seq")
    @GeneratedValue(generator = "user_company_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserCompany that = (UserCompany) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
