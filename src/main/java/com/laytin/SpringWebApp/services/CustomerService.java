package com.laytin.SpringWebApp.services;


import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer getCustomerById(int id){
        return customerRepository.findById(id).orElse(null);
    }
    public Customer getCustomerByUsernameOrEmail(String usernameOrEmail){
        return customerRepository.findByEmailOrUsername(usernameOrEmail,usernameOrEmail).orElse(null);
    }
}
