package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Ord;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrdService {
    private final OrdRepository ordRepository;
    @Autowired
    public OrdService(OrdRepository ordRepository) {
        this.ordRepository = ordRepository;
    }
    public List<Ord> getCustomerOrders(Customer customer){
        return ordRepository.findOrdByCustomer(customer, PageRequest.of(1,10,Sort.by("date")));
    }
    public List<Ord> getCustomerOrders(Customer customer,int page,int perPage){
        return ordRepository.findOrdByCustomer(customer, PageRequest.of(page,perPage,Sort.by("date")));
    }
    public List<Product> getOrderProducts(Ord order){
        List<Product> productList = new ArrayList<>();
        order.getOrdproducts().stream().forEach(ordProduct -> productList.add(ordProduct.getProduct()));
        return productList;
    }
}
