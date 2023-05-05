package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.CustomerRole;
import com.laytin.SpringWebApp.security.CustomerDetails;
import com.laytin.SpringWebApp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping
    public String mainPage(){
        return "redirect:/products";
    }
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
        return "redirect:user/"+((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.customer.id")
    @GetMapping("user/{id}")
    public String index(@PathVariable("id") int id ,Model model){
        model.addAttribute("customer", customerService.getCustomer(id));
        return "user/index";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.customer.id")
    @GetMapping("user/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model){
        if(!model.containsAttribute("customer"))
            model.addAttribute("customer", customerService.getCustomer(id));
        return "user/edit";
    }
    @PatchMapping("user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.customer.id")
    public String updateUser(@ModelAttribute("customer") Customer customer, BindingResult errors, @PathVariable("id") int id){
        if(errors.hasErrors()){
            return "user/edit";
        }
        customerService.updateCurrentCustomer(id,customer);
        return "redirect:/user/"+id;
    }
    @GetMapping("user/search")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.customer.id")
    public String searchUserGet(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,
                                @RequestParam(value = "sort", required = false, defaultValue = "Id") String sorting,
                                @RequestParam(value = "dir", required = false, defaultValue = "Desc") String direction,
                                Model m){
        m.addAttribute("customers",customerService.getCustomerList(page,sorting,direction));
        return "user/list";
    }
    @PostMapping("user/search")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.customer.id")
    public String searchUserPost(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,
                                @RequestParam(value = "sort", required = false, defaultValue = "Id") String sorting,
                                @RequestParam(value = "dir", required = false, defaultValue = "Desc") String direction,
                                 @RequestParam(value = "q", required = false, defaultValue = "") String search,
                                 Model m){
        m.addAttribute("customers",customerService.getCustomerList(page,sorting,direction,search));
        return "user/list";
    }
    ///

}
