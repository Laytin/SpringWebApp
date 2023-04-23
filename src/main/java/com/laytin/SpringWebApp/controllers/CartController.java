package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.dto.CartDTORequest;
import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.services.CartService;
import com.laytin.SpringWebApp.util.CartDTOValidator;
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
    private final CartDTOValidator cartDTOValidator;
    @Autowired
    public CartController(CartService cartService, CartDTOValidator cartDTOValidator) {
        this.cartService = cartService;
        this.cartDTOValidator = cartDTOValidator;
    }
    //return main cart page
    @GetMapping()
    public String index(Model model){
        List<CartProduct> cp = cartService.getCart();
        model.addAttribute("addresses", cartService.getAddresses());
        model.addAttribute("products", cp);
        model.addAttribute("request", new CartDTORequest(cp)); // means that all items will be checked at first.
                                                                            // Also need for validation
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
    public String createOrder(@ModelAttribute("request") CartDTORequest cartDTORequest,
                              BindingResult errors, Model model){
        cartDTOValidator.validate(cartDTORequest,errors);
        if(errors.hasErrors()){
            //otherwise we are returning null model in getMapping
            model.addAttribute("addresses", cartService.getAddresses());
            model.addAttribute("products", cartService.getCart());
            return "/cart/index";
        }
        cartService.confirmOrder(cartDTORequest.getProductList(),cartDTORequest.getAddress());
        return "redirect:/order";
    }

}
