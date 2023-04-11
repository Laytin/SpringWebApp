package com.laytin.SpringWebApp.services;


import com.laytin.SpringWebApp.models.Cart;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.repositories.CartRepository;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    //one service one controller.
    //one service many repository
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }
    public Customer getCustomerById(int id){
        return customerRepository.findById(id).orElse(null);
    }
    public Customer getCustomerByUsernameOrEmail(String usernameOrEmail){
        return customerRepository.findByEmailOrUsername(usernameOrEmail,usernameOrEmail).orElse(null);
    }
    @Transactional
    public void createCustomer(Customer customer){
        Customer newCustomer = customer;
        Cart ourcart = new Cart(newCustomer);
        //good practice of 2ways binding
        newCustomer.setCart(ourcart);
        customerRepository.save(newCustomer);
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')") //grants that user is logged in and it's his userinfo
    public void updateCustomer(Customer customer){
        Customer updated = customerRepository.findByUsername(
                ((Customer)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
        ).get();
        customer.setId(updated.getId()); //same id for jpa
        customer.setUsername(updated.getUsername()); //same username (don't give possibility to changing em)
        customer.setCart(updated.getCart()); // to avoid binding errors
        customer.setAddresses(updated.getAddresses());// to avoid binding errors
        customer.setOrds(updated.getOrds());// to avoid binding errors

        customerRepository.save(customer);
    }
}
