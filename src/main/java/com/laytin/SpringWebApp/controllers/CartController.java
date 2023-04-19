package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("cart", cartService.getCart());
        return "cart/index";
    }
    //change concrete product from cart
    @PatchMapping("/{id}")
    public String updateCart(@PathVariable("id") int id, @ModelAttribute CartProduct cartProduct){
        cartService.updateCartProduct(id, cartProduct);
        return "redirect:/cart";
    }
    //delete product from cart
    @DeleteMapping("/{id}")
    public String deleteFromCart(@PathVariable("id") int id, @ModelAttribute("cartproduct")CartProduct cartProduct){
        cartService.deleteCartProduct(id);
        return "redirect:/cart";
    }
    @PostMapping("/confirm")
    public String createOrder(BindingResult errors){
        return "redirect:/order";
    }

}
