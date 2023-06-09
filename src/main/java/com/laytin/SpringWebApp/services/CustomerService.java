package com.laytin.SpringWebApp.services;


import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.CustomerRole;
import com.laytin.SpringWebApp.repositories.CustomerRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    //one service one controller.
    //one service many repository
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger log= LogManager.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void createCustomer(Customer customer, HttpServletRequest request){
        //good practice of 2ways binding
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setCustomer_Role(CustomerRole.ROLE_USER);
        customerRepository.save(customer);
        log.info("Customer has been registered:"+customer.toString()+", IP:"+request.getRemoteAddr());
    }
    @Transactional
    public void updateCurrentCustomer(int id, Customer customer, CustomerDetails principal){
        Customer updated = customerRepository.findById(id).get();
        customer.setId(id);
        customer.setUsername(updated.getUsername());
        customer.setCartproducts(updated.getCartproducts());
        customer.setAddresses(updated.getAddresses());
        customer.setOrds(updated.getOrds());

        if(customer.getFullname()==null || customer.getFullname().equals(""))
            customer.setFullname(updated.getFullname());

        if(customer.getPassword()!=null || !customer.getPassword().equals(""))
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        else
            customer.setPassword(updated.getPassword());

        if(!principal.getAuthorities()
                .toString().replaceAll("[\\[.*?\\]]*","").equals(CustomerRole.ROLE_ADMIN.toString()))
            customer.setCustomer_Role(updated.getCustomer_Role());
        customerRepository.save(customer);
        log.info("Customer has been updated:"+customer.toString()+" by ["+principal.getCustomer().toString()+"]");
    }
    public Customer getCustomer(int id) {
        return customerRepository.findById(id).get();
    }
    public List<Customer> getCustomerList(int page,String sort,String dir){
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Customer> customers = customerRepository.findAll(PageRequest.of(page-1, 10, Sort.by(direction,sort.toLowerCase()))).getContent();
        return customers;
    }
    public List<Customer> getCustomerList(int page,String sort,String dir,String search){
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Customer> customers = customerRepository.findAllByAll(search,PageRequest.of(page-1, 10, Sort.by(direction,sort.toLowerCase())));
        return customers;
    }
}
