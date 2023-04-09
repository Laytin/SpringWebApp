package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.repositories.OrdRepository;
import com.laytin.SpringWebApp.services.OrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrdController {
    private final OrdService ordService;
    @Autowired
    public OrdController(OrdService ordService) {
        this.ordService = ordService;
    }
    // show all orders
    @GetMapping
    public String index(){
        return "hello";
    }
}
