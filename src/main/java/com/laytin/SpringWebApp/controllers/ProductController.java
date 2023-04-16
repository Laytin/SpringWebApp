package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String show(){
        return null;
    }

}
