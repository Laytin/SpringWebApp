package com.laytin.SpringWebApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    public CartController() {
    }
    //return main cart page
    @GetMapping
    public String index(){
        return "hello";
    }
}
