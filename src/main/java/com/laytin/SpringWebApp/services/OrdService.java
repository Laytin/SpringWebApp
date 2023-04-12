package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.OrdDAO;
import com.laytin.SpringWebApp.models.*;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrdService {
    private final OrdRepository ordRepository;
    private final CustomerRepository customerRepository;
    private final OrdDAO ordDAO;
    @Autowired
    public OrdService(OrdRepository ordRepository, CustomerRepository customerRepository, OrdDAO ordDAO) {
        this.ordRepository = ordRepository;
        this.customerRepository = customerRepository;
        this.ordDAO = ordDAO;
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Ord> getCustomerOrders(int page){
        Customer principal =(Customer) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        Customer customer = customerRepository.findByUsername(principal.getUsername()).get();
        return getCustomerOrders(customer,page);
    }
    private List<Ord> getCustomerOrders(Customer customer,int page){
        if(page<1)
            return getCustomerOrders(customer,1);
        List<Ord> result =ordRepository.findOrdByCustomer(customer, PageRequest.of(page,10,Sort.by("takenAt")));
        result.forEach(o -> o.calculateTotal());
        return result;
    }
    ////////////////////////////////////////////////////////////////
    @PreAuthorize("hasRole('ROLE_USER')")
    public Ord getOrder(int id){
        Customer principal =(Customer) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        Customer customer = customerRepository.findByUsername(principal.getUsername()).get();
        return customer.getOrds().stream().filter(ord -> ord.getId()==id).findFirst().orElse(null);
    }
    ////////////////////////////////////////////////////////////////
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createNewOrder(int addressId){
        Customer principal =(Customer) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        Customer customer = customerRepository.findByUsername(principal.getUsername()).get();
        Address address = customer.getAddresses().stream().filter(adr-> adr.getId()==addressId).findFirst().orElse(null);
        List<CartProduct> cartProducts = customer.getCart().getCartproducts();

        if(cartProducts ==null || address==null)
            return;

        List<OrdProduct> ordProducts = new ArrayList<OrdProduct>();
        Ord newOrder = new Ord();
        cartProducts.forEach(cartProduct -> {
            OrdProduct p = new OrdProduct();
            p.setProduct(cartProduct.getProduct());
            p.setOrd(newOrder);
            p.setQuantity(cartProduct.getQuantity());
        });
        newOrder.setOrderedAt(LocalDateTime.now());
        newOrder.setOrdproducts(ordProducts);
        newOrder.setAddress(address);
        newOrder.setStatus(OrderState.COLLECTING_ORDER);
        newOrder.setCustomer(customer);

        ordRepository.save(newOrder);
    }
}
