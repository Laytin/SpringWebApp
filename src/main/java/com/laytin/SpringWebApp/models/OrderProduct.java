package com.laytin.SpringWebApp.models;

public class OrderProduct {
    private int id;
    private Order order; //many products one order
    private Product product_id; // no binding
    private int quantity;
}
