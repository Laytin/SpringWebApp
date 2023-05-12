package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Product product){
        return "products/new";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @PostMapping()
    public String create(@ModelAttribute("product") @Valid Product product, BindingResult result){
        if(result.hasErrors()){
            return "products/new";
        }
        int id = productService.createProduct(product);
        return "redirect:/products/"+id;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id,
                       Model model){
        model.addAttribute("product",productService.getProduct(id));
        return "products/edit";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @PatchMapping("/{id}")
    public String update(@PathVariable("id")int id, @ModelAttribute("product") @Valid Product product, BindingResult result){
        if(result.hasErrors()){
            return "products/edit";
        }
        productService.updateProduct(id, product);
        return "redirect:/products/"+id;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @PostMapping("/{id}")
    public String uploadImage(@PathVariable("id")int id, @RequestParam("file") List<MultipartFile> files){
        productService.saveImages(id,files);
        return "redirect:/products/"+id;
    }
}
