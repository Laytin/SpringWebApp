package com.laytin.SpringWebApp.models;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private List<Address> addresses; // one user many addresses
    private List<Order> orders; //one user many orders
    private Cart cart; // one user one cart
}
