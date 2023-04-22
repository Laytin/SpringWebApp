package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.CartDAO;
import com.laytin.SpringWebApp.models.*;
import com.laytin.SpringWebApp.repositories.AddressRepository;
import com.laytin.SpringWebApp.repositories.CartProductRepository;
import com.laytin.SpringWebApp.repositories.OrdProductRepository;
import com.laytin.SpringWebApp.repositories.OrdRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartProductRepository cartProductRepository;
    private final AddressRepository addressRepository;
    private final CartDAO cartDAO;
    private final OrdRepository ordRepository;
    private final OrdProductRepository ordProductRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CartService(CartProductRepository cartProductRepository, AddressRepository addressRepository, CartDAO cartDAO, EntityManager entityManager, OrdRepository ordRepository, OrdProductRepository ordProductRepository) {
        this.cartProductRepository = cartProductRepository;
        this.addressRepository = addressRepository;
        this.cartDAO = cartDAO;
        this.entityManager = entityManager;
        this.ordRepository = ordRepository;
        this.ordProductRepository = ordProductRepository;
    }
    //1+n
    @Transactional
    public List<CartProduct> getCart(){
        int customerId = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        List<CartProduct> cartProduct = cartDAO.getPreloadedCartProducts(customerId);
/*        cartProduct.stream().forEach(cp -> {
            if(cp.getQuantity()>cp.getProduct().getQuantity()){
                cp.setQuantity(cp.getProduct().getQuantity());
                cartProductRepository.save(cp);
            }
        });*/
        return cartProduct;
    }
    public List<Address> getAddresses(){
        int customerId = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        return addressRepository.findByCustomerId(customerId);
    }

    public void updateCartProduct(int id, CartProduct cartProduct) {
        CartProduct cp = cartProductRepository.findById(id).get();
        if(cp.getQuantity()==cartProduct.getQuantity())
            return;

        //check that customer can change cartproduct only from their cart
        Hibernate.initialize(cp.getCustomer());
        if(((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId() != cp.getCustomer().getId())
            return;

        cp.setQuantity(Math.min(cartProduct.getQuantity(),cp.getProduct().getQuantity()));
        cartProductRepository.save(cp);
    }
    public void deleteCartProduct(int id){
        CartProduct cp = cartProductRepository.findById(id).get();
        Hibernate.initialize(cp.getCustomer());
        if(((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId() != cp.getCustomer().getId())
            return;
        cartProductRepository.delete(cp);
    }
    @Transactional
    public void confirmOrder(List<CartProduct> cartRequest, Address choosedAddress,BindingResult result){
        Session session =entityManager.unwrap(Session.class);
        Customer principal = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer();
        Customer loadedOwner = session.load(Customer.class, principal.getId());

        //Validating products
/*
        cartRequest.stream().forEach(cartProduct -> {
            cartProductValidator.validate(cartProduct,result);
        });
        if(result.hasErrors())
            return;
*/

        Ord order =  new Ord();
        //products binding (2 side)
        List<OrdProduct> ordProducts = cartRequest.stream().map(cartProduct ->
                new OrdProduct(order,cartProduct.getProduct(),cartProduct.getQuantity())).collect(Collectors.toList());
        order.setOrdproducts(ordProducts);

        // customer binding (2 side)
        Hibernate.initialize(loadedOwner.getOrds());
        order.setCustomer(loadedOwner);
        loadedOwner.addOrder(order);
        //another info
        order.setAddress(choosedAddress);
        order.setOrderedAt(new Date());
        order.setStatus(OrderState.COLLECTING_ORDER);

        ordRepository.save(order);
        ordProductRepository.saveAll(ordProducts);
        //if ok, remove count of bought products from avaible quantity

    }
}

