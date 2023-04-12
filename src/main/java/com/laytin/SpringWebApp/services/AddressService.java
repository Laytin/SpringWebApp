package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.repositories.AddressRepository;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void addAddress(Address address){
        Customer customerdb = customerRepository.findByUsername(getPrincipialUsername()).get(); //real db user
        address.setCustomer(customerdb); //double side binding
        customerdb.addAddress(address); //double side binding
        addressRepository.save(address);
        customerRepository.save(customerdb);
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteAddress(int addressId){
        Optional<Address> address = addressRepository.findById(addressId);
        if(address.isEmpty() || !address.get().getCustomer().getUsername().equals(
                getPrincipialUsername())
        )
            return;
        addressRepository.delete(address.get());
        address.get().getCustomer().removeAddress(address.get());
        customerRepository.save(address.get().getCustomer());
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateAddress(Address address, int id){
        Address adrToBeUpdated = addressRepository.findById(id).get();
        if(adrToBeUpdated.getCustomer().getUsername().equals(
                getPrincipialUsername())
        )
            return;
        address.setId(id);
        address.setCustomer(adrToBeUpdated.getCustomer());
        addressRepository.save(address);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Address> getAddresses(){
        Customer customer  = customerRepository.findByUsername(getPrincipialUsername()).get();
        return customer.getAddresses();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public Address getAddress(int id){
        Customer customer  = customerRepository.findByUsername(getPrincipialUsername()).get();
        Address address = customer.getAddresses().stream().filter(adr -> id==adr.getId()).findFirst().orElse(null);
        return address;
    }
    private String getPrincipialUsername(){
        return ((Customer)SecurityContextHolder. getContext(). getAuthentication().getPrincipal()).getUsername();
    }
    //get one
    //get List
}
