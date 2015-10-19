package com.pgs.taxidriver.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jpadjasek on 2015-08-31.
 */
@Entity
public class Car {
    @Id
    private Long id;
    private String plateNumber;
    private String brandModel;
    private Date yearOfProd;
    private Boolean status;
    private String capacity;
    private Short numberOfSeats;
    private Company company;
    private User driver;
    private Double latitude;
    private Double longitude;
    private Boolean active;

    @Transient
    private Double time;

    public Car() {
    }

    public Car(String plateNumber, String brandModel, Date yearOfProd, Boolean status, String capacity, Short numberOfSeats,
               Company company, User driver, Double latitude, Double longitude) {
        this.plateNumber = plateNumber;
        this.brandModel = brandModel;
        this.yearOfProd = yearOfProd;
        this.status = status;
        this.capacity = capacity;
        this.numberOfSeats = numberOfSeats;
        this.company = company;
        this.driver = driver;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Car(Long id, String plateNumber, String brandModel, Date yearOfProd, Boolean status, String capacity, Short numberOfSeats,
               Company company, User driver, Double latitude, Double longitude) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.brandModel = brandModel;
        this.yearOfProd = yearOfProd;
        this.status = status;
        this.capacity = capacity;
        this.numberOfSeats = numberOfSeats;
        this.company = company;
        this.driver = driver;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @SequenceGenerator(allocationSize = 1, initialValue = 3, sequenceName = "car_id_seq", name = "car_id_seq")
    @GeneratedValue(generator = "car_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "plate_number")
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Basic
    @Column(name = "brand_model")
    public String getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(String brandModel) {
        this.brandModel = brandModel;
    }

    @Basic
    @Column(name = "year_of_prod")
    public Date getYearOfProd() {
        return yearOfProd;
    }

    public void setYearOfProd(Date yearOfProd) {
        this.yearOfProd = yearOfProd;
    }

    @Basic
    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "capacity")
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "number_of_seats")
    public Short getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Short numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Transient
    public Double getTime() {
        return time;
    }

    @Transient
    public void setTime(Double time) {
        this.time = time;
    }

    @Basic
    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Car car = (Car) o;

        if (id != null ? !id.equals(car.id) : car.id != null)
            return false;
        if (plateNumber != null ? !plateNumber.equals(car.plateNumber) : car.plateNumber != null)
            return false;
        if (brandModel != null ? !brandModel.equals(car.brandModel) : car.brandModel != null)
            return false;
        if (yearOfProd != null ? !yearOfProd.equals(car.yearOfProd) : car.yearOfProd != null)
            return false;
        if (status != null ? !status.equals(car.status) : car.status != null)
            return false;
        if (capacity != null ? !capacity.equals(car.capacity) : car.capacity != null)
            return false;
        if (numberOfSeats != null ? !numberOfSeats.equals(car.numberOfSeats) : car.numberOfSeats != null)
            return false;
        if (longitude != null ? !longitude.equals(car.longitude) : car.longitude != null)
            return false;
        if (latitude != null ? !latitude.equals(car.latitude) : car.latitude != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (plateNumber != null ? plateNumber.hashCode() : 0);
        result = 31 * result + (brandModel != null ? brandModel.hashCode() : 0);
        result = 31 * result + (yearOfProd != null ? yearOfProd.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (numberOfSeats != null ? numberOfSeats.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }
}
