package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.repositories.AddressRepository;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Address> getAddresses(CustomerDetails cd){
        return addressRepository.findByCustomerId(cd.getCustomer().getId());
    }
    public Address getAddress(int id, CustomerDetails cd){
        Optional<Address> adr = addressRepository.findById(id);
        if(!adr.isPresent() || adr.get().getCustomer().getId()!=cd.getCustomer().getId())
            return null;
        return adr.get();
    }
    @Transactional
    public void updateAddress(Address address,int id, CustomerDetails cd){
        Optional<Address> adrToBeUpdated = addressRepository.findById(id);
        if(adrToBeUpdated.isEmpty() || adrToBeUpdated.get().getCustomer().getId()!= cd.getCustomer().getId())
            return;
        address.setId(id);
        address.setCustomer(adrToBeUpdated.get().getCustomer());
        addressRepository.save(address);
    }
    @Transactional
    public void deleteAddress(int addressId, CustomerDetails cd){
        Optional<Address> address = addressRepository.findById(addressId);
        if(address.isEmpty() || address.get().getCustomer().getId()!= cd.getCustomer().getId())
            return;
        Hibernate.initialize(address.get().getCustomer());
        address.get().getCustomer().removeAddress(address.get());
        customerRepository.save(address.get().getCustomer());
        addressRepository.delete(address.get());
    }
    @Transactional
    public void addAddress(Address address, CustomerDetails cd){
        Session session = entityManager.unwrap(Session.class);
        address.setCustomer(session.load(Customer.class,cd.getCustomer().getId()));
        addressRepository.save(address);
    }
}
