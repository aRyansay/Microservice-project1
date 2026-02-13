package com.employee.EMPLOYEE.model.dto;


import com.employee.EMPLOYEE.enums.AddresssType;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
public class AddressDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long empId;
    private String street;
    private Long pincode;
    private String city;
    private String country;

    @Enumerated(EnumType.STRING)
    private AddresssType addresssType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AddresssType getAddresssType() {
        return addresssType;
    }

    public void setAddresssType(AddresssType addresssType) {
        this.addresssType = addresssType;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", empId=" + empId +
                ", street='" + street + '\'' +
                ", pincode=" + pincode +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", addresssType=" + addresssType +
                '}';
    }

    public AddressDto() {
    }
}
