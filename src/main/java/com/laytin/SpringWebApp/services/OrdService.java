package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.OrdDAO;
import com.laytin.SpringWebApp.models.*;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger log = LogManager.getLogger(OrdService.class);
    @Autowired
    public OrdService(OrdRepository ordRepository, CustomerRepository customerRepository, OrdDAO ordDAO) {
        this.ordRepository = ordRepository;
        this.customerRepository = customerRepository;
        this.ordDAO = ordDAO;
    }
    public List<Ord> getCustomerOrders(int page, CustomerDetails principal){
        List<Ord> orders = ordDAO.getOrders(principal.getCustomer(), PageRequest.of(page-1,5));
        orders.forEach(Ord::calculateTotal);
        return orders;
    }
    public Ord getOrder(int id, CustomerDetails principal){
        Ord order = ordDAO.getOrder(id);
        if (order==null)
            return null;
        if(order.getCustomer().getId()!=principal.getCustomer().getId() && principal.getAuthorities()
                .toString().replaceAll("[\\[.*?\\]]*","").equals(CustomerRole.ROLE_USER.toString())){
            return null;
        }
        order.calculateTotal();
        return order;
    }
    @Transactional
    public void setOrderStatus(int id,Ord order, CustomerDetails cd){
        Ord changed = ordRepository.findOrdById(id);
        changed.setStatus(order.getStatus());
        ordRepository.save(changed);
        log.info("Order status has been updated:"+order.toString()+" by ["+cd.getCustomer().toString()+"]");
    }
}
