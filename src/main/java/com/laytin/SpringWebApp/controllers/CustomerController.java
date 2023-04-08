package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Ord;
import com.laytin.SpringWebApp.models.OrdProduct;
import com.laytin.SpringWebApp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
}
