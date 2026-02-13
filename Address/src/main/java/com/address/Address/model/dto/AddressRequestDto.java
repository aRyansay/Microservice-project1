package com.address.Address.model.dto;

import com.address.Address.model.enums.AddresssType;
import jakarta.persistence.*;

public class AddressRequestDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
}
