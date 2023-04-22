package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        model.addAttribute("addresses", cartService.getAddresses());
        model.addAttribute("products", cartService.getCart());
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
    public String deleteFromCart(@PathVariable("id") int id){
        System.out.println("asda");
        cartService.deleteCartProduct(id);
        return "redirect:/cart";
    }
    @PostMapping("/conf")
    public String createOrder(@ModelAttribute("products") List<CartProduct> products,
                              @ModelAttribute("address")Address address,
                              BindingResult errors, Model model){
/*        cartService.confirmOrder(products,address, errors);
        if(errors.hasErrors()){
            return "/cart/index";
        }*/
        return "redirect:/order";
    }

}
