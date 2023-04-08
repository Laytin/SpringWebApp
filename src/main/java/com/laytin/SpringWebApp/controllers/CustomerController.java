package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.services.CustomerService;
import com.laytin.SpringWebApp.services.OrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CustomerController {
    private final CustomerService customerService;
    private final OrdService ordService;
    @Autowired
    public CustomerController(CustomerService customerService, OrdService ordService) {
        this.customerService = customerService;
        this.ordService = ordService;
    }
    @GetMapping
    public String index(){
        return "hello";
    }
}
