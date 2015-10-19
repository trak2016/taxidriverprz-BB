package com.pgs.taxidriver.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jpadjasek on 2015-08-31.
 */
@Entity
public class Course {
    @Id
    private Long id;
    private Float cost;
    private Float distance;
    private User user;
    private Date date;
    private Integer customerPhoneNumber;

    public Course(Float cost, Float distance, User user, Date date) {
        this.cost = cost;
        this.distance = distance;
        this.user = user;
        this.date = date;
    }

    public Course() {

    }

    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "course_id_seq", name = "course_id_seq")
    @GeneratedValue(generator = "course_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cost")
    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "distance")
    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "customer_phone_number")
    public Integer getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(Integer customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Course course = (Course) o;

        if (id != null ? !id.equals(course.id) : course.id != null)
            return false;
        if (cost != null ? !cost.equals(course.cost) : course.cost != null)
            return false;
        if (distance != null ? !distance.equals(course.distance) : course.distance != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return "driver: " + this.getUser().getName() + " " + this.getUser().getLastName() + ", cost: "
                + this.getCost() + ", distance: " + this.getDistance() + ", date: " + format.format(this.getDate());
    }
}
