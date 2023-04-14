package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.OrdDAO;
import com.laytin.SpringWebApp.models.*;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Order;
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
    // TODO: fix n+1
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Ord> getCustomerOrders(int page){
        int customerId =((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        List<Ord> orders = ordRepository.findByCustomerIdOrderByIdDesc(customerId, PageRequest.of(page,5));
        orders.forEach(order -> order.calculateTotal());
        return orders;
    }
    ////////////////////////////////////////////////////////////////
    @PreAuthorize("hasRole('ROLE_USER')")
    public Ord getOrder(int id){
        Customer principal =((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer();
        Ord order = ordRepository.findOrdById(id);
        if(order.getCustomer().getId()!=principal.getId()){
            return null;
        }
        Hibernate.initialize(order.getOrdproducts()); // prepare for calculation
        Hibernate.initialize(order.getAddress()); // prepare for calculation
        order.calculateTotal();
        return order;
    }
    public List<Product> getOrderProducts(Ord order){
        return order.getOrdproducts().stream().map(p->p.getProduct()).collect(Collectors.toList());
    }
    ////////////////////////////////////////////////////////////////
    @PreAuthorize("hasRole('ROLE_USER')")
    public void createNewOrder(int addressId){
/*        Customer principal =(Customer) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
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
        newOrder.setOrderedAt(new Date());
        newOrder.setOrdproducts(ordProducts);
        newOrder.setAddress(address);
        newOrder.setStatus(OrderState.COLLECTING_ORDER);
        newOrder.setCustomer(customer);

        ordRepository.save(newOrder);*/
    }
}
