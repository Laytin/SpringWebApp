package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.Cart;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    public CartService(CartRepository cartRepository, CartProductRepository cartProductRepository, ProductRepository productRepository, ItemRepository itemRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public Cart getCart(){
        Cart cart  = customerRepository.findByUsername(getPrincipialUsername()).get().getCart();
        cart.calculateTotal();
        //List<Product> productList= cart.getCartproducts().stream().map(cartProduct -> cartProduct.getProduct()).collect(Collectors.toList());
        return cart;
    }
    private String getPrincipialUsername(){
        return ((Customer) SecurityContextHolder. getContext(). getAuthentication().getPrincipal()).getUsername();
    }
}
