package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.security.CustomerDetails;
import com.laytin.SpringWebApp.services.AddressService;
import com.laytin.SpringWebApp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @GetMapping()
    public String index(Model model, Authentication auth){
        model.addAttribute("addresses", addressService.getAddresses((CustomerDetails) auth.getPrincipal()));
        return "address/index";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model, Authentication auth){
        Address adr = addressService.getAddress(id,(CustomerDetails) auth.getPrincipal());
        if(adr==null){
            return "redirect:/address";
        }
        model.addAttribute("address", adr);
        return "address/edit";
    }
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("address")Address address,Authentication auth){
        addressService.updateAddress(address,id, (CustomerDetails) auth.getPrincipal());
        return "redirect:/address";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id,Authentication auth){
        addressService.deleteAddress(id,(CustomerDetails) auth.getPrincipal());
        return "redirect:/address";
    }
    @GetMapping("/new")
    public String add(@ModelAttribute("address") Address address){
        return "address/new";
    }
    @PostMapping()
    public String add(@ModelAttribute("address") Address address, BindingResult result,Authentication auth){
        if(result.hasErrors()){
            return "address/new";
        }
        addressService.addAddress(address,(CustomerDetails) auth.getPrincipal());
        return "redirect:/address";
    }
}
