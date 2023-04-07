package com.laytin.SpringWebApp.models;

import java.util.List;

public class Address {
    private int id;
    private User user; // one user many addresses
    private String country;
    private String city;
    private String state;
    private int zipcode;
    private long phoneNumber;
    private String firstName;
    private String lastName;
}
