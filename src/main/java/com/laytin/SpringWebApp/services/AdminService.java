package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final OrdRepository ordRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    @Autowired
    public AdminService(AddressRepository addressRepository, CartRepository cartRepository, OrdRepository ordRepository, CustomerRepository customerRepository, ProductRepository productRepository, ItemRepository itemRepository) {
        this.addressRepository = addressRepository;
        this.cartRepository = cartRepository;
        this.ordRepository = ordRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }
}
