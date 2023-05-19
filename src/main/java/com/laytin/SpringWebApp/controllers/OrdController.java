package com.laytin.SpringWebApp.controllers;

import com.laytin.SpringWebApp.models.Ord;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import com.laytin.SpringWebApp.services.OrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

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
    public String index(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page, Model model, Authentication auth){
        model.addAttribute("orders",ordService.getCustomerOrders(page<1?1:page,(CustomerDetails) auth.getPrincipal()));
        return "order/index";
    }
    @GetMapping("/{id}")
    public String showOrder(@PathVariable("id") int id, Model model, Authentication auth){
        Ord order = ordService.getOrder(id);
        if(order==null)
            return "redirect:/order";
        model.addAttribute("order",order);
        return "order/id";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("order",ordService.getOrder(id));
        return "order/edit";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("order") Ord order){
        ordService.setOrderStatus(id,order);
        return "redirect:/order/"+id;
    }
}
