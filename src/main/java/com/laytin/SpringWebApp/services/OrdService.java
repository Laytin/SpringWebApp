package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.OrdDAO;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Ord;
import com.laytin.SpringWebApp.models.OrdProduct;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Ord> getCustomerOrders(int customerId){
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()){
            // TODO: call exception !!!
            return null;
        }
        return getCustomerOrders(customer.get());
    }
    public List<Ord> getCustomerOrders(Customer customer){
        return getCustomerOrders(customer,1);
    }
    //@PreAuthorize("#customer.username == authentication.principal.username")
    public List<Ord> getCustomerOrders(Customer customer,int page){
        if(page<1)
            return getCustomerOrders(customer);

        return ordRepository.findOrdByCustomer(customer, PageRequest.of(page,10,Sort.by("date")));
    }
    ////////////////////////////////////////////////////////////////
    public List<Product> getOrderProducts(int orderId){
        return getOrderProducts(ordRepository.findOrdById(orderId));
    }
    //  |
    //  \/
    //@PreAuthorize("#order.customer.username == authentication.principal.username")
    public List<Product> getOrderProducts(Ord order){
        List<Product> result  = convertFromOrdProduct(order.getOrdproducts());
        //need to cal calculate total, but after sql execution in List<Product>
        // (need to be load to persist context all products with prices)
        order.calculateTotal();
        return result;
    }
    //  |
    //  \/
    private List<Product> convertFromOrdProduct(List<OrdProduct> ordProducts){
        List<Product> result = new ArrayList<>();
        ordProducts.stream().forEach((ordProduct) -> result.add(ordProduct.getProduct()));
        return result;
    }
    ////////////////////////////////////////////////////////////////

}
