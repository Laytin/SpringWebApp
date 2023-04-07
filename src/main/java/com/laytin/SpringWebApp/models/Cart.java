package com.laytin.SpringWebApp.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

public class Cart {
    private int id;
    private User user; // one user one cart
    private List<CartProduct> products; //one cart many products
    @Transient
    private int total;
}
