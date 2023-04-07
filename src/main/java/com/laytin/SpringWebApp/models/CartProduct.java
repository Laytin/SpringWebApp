package com.laytin.SpringWebApp.models;

public class CartProduct {
    private int id;
    private Cart cart_id; // many CartProducts one cart
    private Product product_id;
    private int quantity;
}
