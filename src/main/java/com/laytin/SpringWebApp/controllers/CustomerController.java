package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.services.CustomerService;
import com.laytin.SpringWebApp.services.OrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    //Show User profile with info and buttons(my address, my orders, my cart)
    @GetMapping
    public String index(){
        return "hello";
    }
}
