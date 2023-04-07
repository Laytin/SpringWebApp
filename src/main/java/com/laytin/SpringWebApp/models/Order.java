package com.laytin.SpringWebApp.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

public class Order {
    private int id;
    private List<OrderProduct> products; //one order many orderProducts
    private User user;
    private Address address;
    private OrderState status;
    @Transient
    private int total;
}
