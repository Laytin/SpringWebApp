package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    //return main cart page
    @GetMapping()
    public String index(Model model){
        model.addAttribute("cart", cartService.getCart());
        return "cart/index";
    }
    //change concrete product from cart
    @PatchMapping("/{id}")
    public String updateCart(@PathVariable("id") String id, Model model){
        System.out.println(id);
        return "redirect:/cart";
    }
    //delete product from cart
    @DeleteMapping("/{id}")
    public String deleteFromCart(@PathVariable("id") String id, @ModelAttribute("cartproduct")CartProduct cartProduct){
        System.out.println(id);
        return "redirect:/cart";
    }

    @GetMapping("/confirm")
    public String confirmationPage(Model model){
        model.addAttribute("addresses", cartService.getAddresses());
        return "cart/confirmation";
    }
    //confirm cart before do order to validate all items
    @PatchMapping("")
    public String confirmCart(){
        return null;
    }
    @PostMapping("/confirm")
    public String createOrder(){
        return "redirect:/order";
    }

}
