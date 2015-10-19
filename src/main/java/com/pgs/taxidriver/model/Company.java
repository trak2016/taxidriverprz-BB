package com.pgs.taxidriver.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jpadjasek on 2015-08-31.
 */
@Entity
public class Company {
    @Id
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String logo;
    private Boolean status;
    private String vat_id;
    private String country;
    private String city;
    private String zip;
    private Set<Car> cars;
    private Set<UserCompany> owners;
    public Company() {

    }

    public Company(String logo, String address, String phone, String name) {
        this.logo = logo;
        this.address = address;
        this.phone = phone;
        this.name = name;
    }

    public Company(Long id,String logo, String address, String phone, String name, Boolean status) {
       this.id=id;
        this.logo = logo;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.status = status;
    }

    public Company(String logo, String address, String phone, String name, Boolean status, String vat_id, String country, String city, String zip) {
        this.logo = logo;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.status = status;
        this.vat_id = vat_id;
        this.country = country;
        this.city = city;
        this.zip = zip;
    }

    public Company(Long id, String logo, String address, String phone, String name, Boolean status, String vat_id, String country, String city, String zip) {
        this.id=id;
        this.logo = logo;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.status=status;
        this.vat_id = vat_id;
        this.country = country;
        this.city = city;
        this.zip = zip;
    }

    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 3, sequenceName = "company_id_seq", name = "company_id_seq")
    @GeneratedValue(generator = "company_id_seq", strategy = GenerationType.SEQUENCE)
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
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
    @Column(name = "vat_id")
    public String getVatId() {
        return vat_id;
    }

    public void setVatId(String vat_id) {
        this.vat_id = vat_id;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "zip")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Company company = (Company) o;

        if (id != null ? !id.equals(company.id) : company.id != null)
            return false;
        if (name != null ? !name.equals(company.name) : company.name != null)
            return false;
        if (phone != null ? !phone.equals(company.phone) : company.phone != null)
            return false;
        if (address != null ? !address.equals(company.address) : company.address != null)
            return false;
        if (logo != null ? !logo.equals(company.logo) : company.logo != null)
            return false;
        if (status != null ? !status.equals(company.status) : company.status != null)
            return false;
        if (vat_id != null ? !vat_id.equals(company.vat_id) : company.vat_id != null)
            return false;
        if (country != null ? !country.equals(company.country) : company.country != null)
            return false;
        if (city != null ? !city.equals(company.city) : company.city != null)
            return false;
        if (zip != null ? !zip.equals(company.zip) : company.zip != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (vat_id != null ? vat_id.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "company")
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    public Set<UserCompany> getOwners() {
        return owners;
    }

    public void setOwners(Set<UserCompany> owners) {
        this.owners = owners;
    }
}
