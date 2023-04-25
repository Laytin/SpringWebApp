package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")

public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    // product list. Main menu
    @GetMapping()
    public String index(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,
                        @RequestParam(value = "sort", required = false, defaultValue = "Id") String sorting,
                        @RequestParam(value = "dir", required = false, defaultValue = "Desc") String direction,
                        @RequestParam(value = "search", required = false, defaultValue = "") String search,
                        Model model){
        if(search.equals("")){
            model.addAttribute("products",productService.getProducts(page,sorting,direction));
        }else{
            model.addAttribute("products",productService.getProducts(page,sorting,direction,search));
        }
        return "products/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id,
                       Model model){
        Product product = productService.getProduct(id);
        model.addAttribute("product",product);
        model.addAttribute("avaibleColors",productService.getProductsByName(product.getName()));
        if(!model.containsAttribute("cartproduct")){
            model.addAttribute("cartproduct",new CartProduct());
        }
        return "products/show";
    }
    @PostMapping("/addtocart")
    public String addToCart(@ModelAttribute("cartproduct") @Valid CartProduct cartProduct,
                            BindingResult result,
                            RedirectAttributes ra){
        if(result.hasErrors()){
            ra.addFlashAttribute("org.springframework.validation.BindingResult.cartproduct", result);
            ra.addFlashAttribute("cartproduct", cartProduct);
            return "redirect:/products/"+cartProduct.getProduct().getId();
        }
        productService.addProductToCart(cartProduct);
        return "redirect:/products/";
    }
}
