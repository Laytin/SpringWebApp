package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Ord;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import com.laytin.SpringWebApp.services.OrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrdController {
    private final OrdService ordService;
    @Autowired
    public OrdController(OrdService ordService) {
        this.ordService = ordService;
    }
    // show all orders
    @GetMapping()
    public String index(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page, Model model){
        if(page<1)
            page=1;
        model.addAttribute("orders",ordService.getCustomerOrders(page-1));
        return "order/index";
    }
    @GetMapping("/{id}")
    public String showOrder(@PathVariable("id") int id, Model model){
        Ord order = ordService.getOrder( id);
        if(order==null)
            return "redirect:/order";
        model.addAttribute("order",order);
        model.addAttribute("address",order.getAddress());
        model.addAttribute("products",ordService.getOrderProducts(order));
        return "order/id";
    }
}
