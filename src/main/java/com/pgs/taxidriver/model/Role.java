package com.pgs.taxidriver.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jpadjasek on 2015-08-31.
 */
@Entity
public class Role {
    @Id
    private Long id;
    private String name;
    private Short priority;
    private Set<UserRole> users;

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
    @Column(name = "priority")
    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null)
            return false;
        if (name != null ? !name.equals(role.name) : role.name != null)
            return false;
        if (priority != null ? !priority.equals(role.priority) : role.priority != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "role")
    public Set<UserRole> getUsers() {
        return users;
    }

    public void setUsers(Set<UserRole> users) {
        this.users = users;
    }
}
