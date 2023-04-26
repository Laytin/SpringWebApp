package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.CustomerRole;
import com.laytin.SpringWebApp.security.CustomerDetails;
import com.laytin.SpringWebApp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "auth/registration";
    }
    @PostMapping("auth/registration")
    public String performRegistration(@ModelAttribute("customer") @Valid Customer customer,
                                      BindingResult bindingResult) {

        customerService.createCustomer(customer);

        return "redirect:/auth/login";
    }
    @GetMapping("user")
    public String redirectUser(){
        return "redirect:/user/"+((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
    }
    @GetMapping("user/{id}")
    public String index(Model model){
        model.addAttribute("customer", customerService.getCurrentCustomer());
        return "user/index";
    }

    @GetMapping("user/{id}/edit")
    public String editUser(Model model){
        if(!model.containsAttribute("customer"))
            model.addAttribute("customer", customerService.getCurrentCustomer());

        if(((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getCustomer_Role() == CustomerRole.ROLE_ADMIN)
            return "user/editAdm";
        return "user/edit";
    }
    @PatchMapping("user/{id}")
    public String updateUserUser(@ModelAttribute("customer") @Valid Customer customer, BindingResult errors, RedirectAttributes ra){
        if(errors.hasErrors()){
            ra.addFlashAttribute("org.springframework.validation.BindingResult.customer", errors);
            ra.addFlashAttribute("customer",customer);
            return "redirect:/user";
        }
        customerService.updateCurrentCustomer(customer);
        return "redirect:/user";
    }
    @GetMapping
    public String mainPage(){
        return "redirect:/products";
    }
    ///

}
