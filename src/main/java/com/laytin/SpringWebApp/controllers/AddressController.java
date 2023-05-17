package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.security.CustomerDetails;
import com.laytin.SpringWebApp.services.AddressService;
import com.laytin.SpringWebApp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String index(Model model, Principal principal){
        model.addAttribute("addresses", addressService.getAddresses((CustomerDetails) principal));
        return "address/index";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model, Principal principal){
        Address adr = addressService.getAddress(id,(CustomerDetails) principal);
        if(adr==null){
            return "redirect:/address";
        }
        model.addAttribute("address", adr);
        return "address/edit";
    }
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("address")Address address,Principal principal){
        addressService.updateAddress(address,id, (CustomerDetails) principal);
        return "redirect:/address";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id,Principal principal){
        addressService.deleteAddress(id,(CustomerDetails) principal);
        return "redirect:/address";
    }
    @GetMapping("/new")
    public String add(@ModelAttribute("address") Address address){
        return "address/new";
    }
    @PostMapping()
    public String add(@ModelAttribute("address") Address address, BindingResult result,Principal principal){
        if(result.hasErrors()){
            return "address/new";
        }
        addressService.addAddress(address,(CustomerDetails) principal);
        return "redirect:/address";
    }
}
