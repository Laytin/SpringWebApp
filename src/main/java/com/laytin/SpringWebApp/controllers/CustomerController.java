package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping()
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    //Show User profile with info and buttons(my address, my orders, my cart)
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

    @GetMapping("user")
    public String index(Model model){
        model.addAttribute("customer", customerService.getCurrentCustomer());
        return "/user/user";
    }

    @GetMapping("user/edit")
    public String editUser(Model model){
        model.addAttribute("customer", customerService.getCurrentCustomer());
        return "/user/edit";
    }
    @PatchMapping("user/edit")
    public String updateUser(@ModelAttribute("customer") @Valid Customer customer, BindingResult errors){
        System.out.println("asd");
        customerService.updateCurrentCustomer(customer);
        return  "redirect:/user";
    }
    ///
}
