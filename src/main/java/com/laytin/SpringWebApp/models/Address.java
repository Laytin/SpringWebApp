package com.laytin.SpringWebApp.models;

public class Address {
    private int id;
    private Customer customer; // one user many addresses
    private String country;
    private String city;
    private String state;
    private String firstname;
    private String lastname;
    private int zipcode;
    private long phoneNumber;
}
