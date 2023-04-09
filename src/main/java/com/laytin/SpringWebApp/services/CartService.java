package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.repositories.CartProductRepository;
import com.laytin.SpringWebApp.repositories.CartRepository;
import com.laytin.SpringWebApp.repositories.ItemRepository;
import com.laytin.SpringWebApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    @Autowired
    public CartService(CartRepository cartRepository, CartProductRepository cartProductRepository, ProductRepository productRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }
}
