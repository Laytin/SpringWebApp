package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if(customer.isEmpty())
            throw new UsernameNotFoundException(username);
        return new CustomerDetails(customer.get());
    }
}
