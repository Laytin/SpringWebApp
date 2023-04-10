package com.laytin.SpringWebApp.services;


import com.laytin.SpringWebApp.models.Cart;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.repositories.CartRepository;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //@PreAuthorize("#customer.username == authentication.principal.username")
    public void updateCustomer(Customer customer){

        Customer updated = customerRepository.findById(customer.getId()).get();
        if(!customer.getCustomerRole().equals(updated.getCustomerRole())){
            //call exc
            //if try to change role from user service, not administrator
            return;
        }
        customer.setId(updated.getId());
        customer.setCart(updated.getCart());
        customer.setAddresses(updated.getAddresses());
        customer.setOrds(updated.getOrds());

        customerRepository.save(customer);
    }
}
