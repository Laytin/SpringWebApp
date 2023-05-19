package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.OrdDAO;
import com.laytin.SpringWebApp.models.*;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<Ord> getCustomerOrders(int page){
        int customerId =((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        List<Ord> orders = ordDAO.getOrders(customerId, PageRequest.of(page-1,5));
        orders.forEach(Ord::calculateTotal);
        return orders;
    }
    public Ord getOrder(int id){
        CustomerDetails principal =((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal());
        Ord order = ordRepository.findOrdById(id);
        if(order.getCustomer().getId()!=principal.getCustomer().getId() && principal.getAuthorities()
                .toString().replaceAll("[\\[.*?\\]]*","").equals(CustomerRole.ROLE_USER.toString())){
            return null;
        }
        Hibernate.initialize(order.getOrdproducts()); // prepare for calculation
        Hibernate.initialize(order.getOrdAddress()); // prepare for calculation
        order.calculateTotal();
        return order;
    }
    public List<Product> getOrderProducts(Ord order){
        return order.getOrdproducts().stream().map(p->p.getProduct()).collect(Collectors.toList());
    }
    @Transactional
    public void setOrderStatus(int id,Ord order){
        Ord changed = ordRepository.findOrdById(id);
        changed.setStatus(order.getStatus());
        ordRepository.save(changed);
    }
}
