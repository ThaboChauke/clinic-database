package com.chauke.clinicdatabase.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class Patient {

    private String id;

    @NotEmpty
    private String fullName;

    @NotEmpty
//    @DateTimeFormat(fallbackPatterns = "DD/MM/YYYY")
    private String dateOfBirth;

    @NotEmpty
    private String phoneNumber;

    @Email
    private String email;

    @NotEmpty
    private String address;

    public Patient() { }

    public Patient(String id, String fullName, String dateOfBirth, String phoneNumber, String email, String address) {
        this.fullName = fullName;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
