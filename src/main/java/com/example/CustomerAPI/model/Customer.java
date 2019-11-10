package com.example.CustomerAPI.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Customer {

    @NotBlank
    private final String driverLicense;
    @NotBlank
    private String name;
    private String address;
    private String city;
    private String creaditCardNumber;
    private String phoneNumber;

    @NotNull
    private final LocalDate dateOfBith;
    private int age;

    public Customer(@JsonProperty("driverLicense") String driverLicense,
                    @JsonProperty("name") String name,
                    @JsonProperty("address") String address,
                    @JsonProperty("city") String city,
                    @JsonProperty("creaditCardNumber") String creaditCardNumber,
                    @JsonProperty("phoneNumber") String phoneNumber,
                    @JsonProperty("dateOfBith") LocalDate dateOfBith) {
        this.driverLicense = driverLicense;
        this.name = name;
        this.address = address;
        this.city = city;
        this.creaditCardNumber = creaditCardNumber;
        this.phoneNumber = phoneNumber;
        this.dateOfBith = dateOfBith;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCreaditCardNumber() {
        return creaditCardNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBith() {
        return dateOfBith;
    }

    public int getAge(){
        LocalDate dOfBirth = this.getDateOfBith();
        if ((dOfBirth != null) ){
            return Period.between(dOfBirth, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
