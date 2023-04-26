package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.repositories.AddressRepository;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Address> getAddresses(){
        Customer customer  = customerRepository.findByUsername(getPrincipialCustomer().getUsername()).get();
        Hibernate.initialize(customer.getAddresses());
        return customer.getAddresses();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public Address getAddress(int id){
        Customer customer  = customerRepository.findByUsername(getPrincipialCustomer().getUsername()).get();
        Hibernate.initialize(customer.getAddresses());
        Address address = customer.getAddresses().stream().filter(adr -> id==adr.getId()).findFirst().orElse(null);
        return address;
    }
    @Transactional
    public void updateAddress(Address address,int id){
        Optional<Address> adrToBeUpdated = addressRepository.findById(id);
        if(adrToBeUpdated.isEmpty() || !adrToBeUpdated.get().getCustomer().getUsername().equals(
                getPrincipialCustomer().getUsername())
        )
            return;
        address.setId(id);
        address.setCustomer(adrToBeUpdated.get().getCustomer());
        addressRepository.save(address);
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteAddress(int addressId){
        Optional<Address> address = addressRepository.findById(addressId);
        if(address.isEmpty() || !address.get().getCustomer().getUsername().equals(
                getPrincipialCustomer().getUsername())
        )
            return;
        Hibernate.initialize(address.get().getCustomer());
        address.get().getCustomer().removeAddress(address.get());
        customerRepository.save(address.get().getCustomer());
        addressRepository.delete(address.get());
    }
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void addAddress(Address address){
        Session session = entityManager.unwrap(Session.class);
        address.setCustomer(session.load(Customer.class,getPrincipialCustomer().getId()));
        addressRepository.save(address);
    }
    private Customer getPrincipialCustomer(){
        return ((CustomerDetails)SecurityContextHolder. getContext(). getAuthentication().getPrincipal()).getCustomer();
    }
    //get one
    //get List
}
