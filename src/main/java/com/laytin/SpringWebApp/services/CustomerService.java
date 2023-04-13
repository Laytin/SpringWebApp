package com.laytin.SpringWebApp.services;


import com.laytin.SpringWebApp.models.Cart;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.CustomerRole;
import com.laytin.SpringWebApp.repositories.CartRepository;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    //one service one controller.
    //one service many repository
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CartRepository cartRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Customer getCurrentCustomer(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() == null)
            return null;
        return customerRepository.findByUsername(((CustomerDetails)authentication.getPrincipal()).getCustomer().getUsername()).orElse(null);
    }
    @Transactional
    public void createCustomer(Customer customer){
        Cart ourcart = new Cart(customer);
        //good practice of 2ways binding
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setCart(ourcart);
        customer.setCustomer_Role(CustomerRole.ROLE_USER);
        customerRepository.save(customer);
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')") //grants that user is logged in and it's his userinfo
    public void updateCurrentCustomer(Customer customer){
        Customer updated = customerRepository.findByUsername(
                ((CustomerDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
        ).get();
        customer.setId(updated.getId()); //same id for jpa
        customer.setUsername(updated.getUsername()); //same username (don't give possibility to changing em)
        customer.setCart(updated.getCart()); // to avoid binding errors
        customer.setAddresses(updated.getAddresses());// to avoid binding errors
        customer.setOrds(updated.getOrds());// to avoid binding errors
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setCustomer_Role(CustomerRole.ROLE_USER);
        customerRepository.save(customer);
    }
}
