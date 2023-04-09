package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.repositories.AddressRepository;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    //add
    //delete
    //get one
    //get List
    //update
}
