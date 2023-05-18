package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.dto.CartDTORequest;
import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.security.CustomerDetails;
import com.laytin.SpringWebApp.services.CartService;
import com.laytin.SpringWebApp.util.CartDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping()
    public String index(Model model, Authentication auth){
        List<CartProduct> cp = cartService.getCart((CustomerDetails) auth.getPrincipal());
        model.addAttribute("addresses", cartService.getAddresses((CustomerDetails) auth.getPrincipal()));
        model.addAttribute("products", cp);
        // cartdto - waiting for req, filling all products in req. Can make a template with checkboxes, which product
        // customer would buy now
        if(!model.containsAttribute("request"))
            model.addAttribute("request", new CartDTORequest(cp));

        return "cart/index";
    }
    @PatchMapping("/{id}")
    public String updateCart(@PathVariable("id") int id, @ModelAttribute CartProduct cartProduct, Authentication auth){
        cartService.updateCartProduct(id, cartProduct,(CustomerDetails) auth.getPrincipal());
        return "redirect:/cart";
    }
    @DeleteMapping("/{id}")
    public String deleteFromCart(@PathVariable("id") int id, Authentication auth){
        cartService.deleteCartProduct(id,(CustomerDetails) auth.getPrincipal());
        return "redirect:/cart";
    }
    @PostMapping("/conf")
    public String createOrder(@ModelAttribute("request") CartDTORequest cartDTORequest,
                              BindingResult result, Model model, RedirectAttributes ra, Authentication auth){
        cartDTOValidator.validate(cartDTORequest,result);
        if(result.hasErrors()){
            ra.addFlashAttribute("org.springframework.validation.BindingResult.request", result);
            ra.addFlashAttribute("request",cartDTORequest);
            return "redirect:/cart";
        }
        cartService.confirmOrder(cartDTORequest.getProductList(),cartDTORequest.getAddress());
        return "redirect:/order";
    }

}
