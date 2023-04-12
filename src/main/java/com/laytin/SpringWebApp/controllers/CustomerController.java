package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.services.CustomerService;
import com.laytin.SpringWebApp.services.OrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    //Show User profile with info and buttons(my address, my orders, my cart)
    @GetMapping("/user/")
    public String index(){
        return "hello";
    }
    @GetMapping("auth/login")
    public String login(){
        return "auth/login";
    }
    @GetMapping("auth/registration")
    public String registrationPage(@ModelAttribute("customer") Customer customer) {
        System.out.println("asd");
        return "auth/registration";
    }
    @PostMapping("auth/registration")
    public String performRegistration(@ModelAttribute("customer") @Valid Customer customer,
                                      BindingResult bindingResult) {

        customerService.createCustomer(customer);

        return "redirect:/auth/login";
    }
}
