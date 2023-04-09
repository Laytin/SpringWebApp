package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.repositories.ItemRepository;
import com.laytin.SpringWebApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, ItemRepository itemRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }
}
