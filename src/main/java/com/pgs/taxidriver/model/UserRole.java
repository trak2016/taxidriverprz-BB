package com.pgs.taxidriver.model;

import javax.persistence.*;

/**
 * Created by jpadjasek on 2015-08-31.
 */
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    private Long id;
    private Role role;
    private User user;

    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "user_role_id_seq", name = "user_role_id_seq")
    @GeneratedValue(generator = "user_role_id_seq", strategy = GenerationType.SEQUENCE)
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

        UserRole userRole = (UserRole) o;

        if (id != null ? !id.equals(userRole.id) : userRole.id != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
