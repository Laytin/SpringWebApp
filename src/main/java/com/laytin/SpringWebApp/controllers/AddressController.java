package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("addresses", addressService.getAddresses());
        return "address/index";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model){
        Address adr = addressService.getAddress(id);
        if(adr==null){
            return "redirect:/address";
        }
        model.addAttribute("address", adr);
        return "address/edit";
    }
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id, @ModelAttribute("address")Address address){
        addressService.updateAddress(address,id);
        return "redirect:/address";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        addressService.deleteAddress(id);
        return "redirect:/address";
    }
    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("address",new Address());
        return "address/new";
    }
    @PostMapping()
    public String add(@ModelAttribute("address") Address address){
        addressService.addAddress(address);
        return "redirect:/address";
    }
}
