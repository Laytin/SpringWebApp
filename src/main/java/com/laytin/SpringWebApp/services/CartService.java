package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dto.CartDTO;
import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.repositories.AddressRepository;
import com.laytin.SpringWebApp.repositories.CartProductRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartProductRepository cartProductRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CartService(CartProductRepository cartProductRepository, AddressRepository addressRepository) {
        this.cartProductRepository = cartProductRepository;
        this.addressRepository = addressRepository;
    }
    //1+n
    @Transactional
    public CartDTO getCart(){
        int customerId = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        List<CartProduct> cartProduct = cartProductRepository.findByCustomerId(customerId);
        CartDTO cart = new CartDTO(cartProduct);
        cart.calculateTotal();
        cartProduct.stream().forEach(cp -> {
            if(cp.getQuantity()>cp.getProduct().getQuantity()){
                cp.setQuantity(cp.getProduct().getQuantity());
                cartProductRepository.save(cp);
            }
        });
        return cart;
    }
    public List<Address> getAddresses(){
        int customerId = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        return addressRepository.findByCustomerId(customerId);
    }
}

