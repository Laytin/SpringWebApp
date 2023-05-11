package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.CartDAO;
import com.laytin.SpringWebApp.models.*;
import com.laytin.SpringWebApp.repositories.*;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final OrdAddressRepository ordAddressRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CartService(CartProductRepository cartProductRepository, AddressRepository addressRepository, CartDAO cartDAO, EntityManager entityManager, OrdRepository ordRepository, OrdProductRepository ordProductRepository,
                       ProductRepository productRepository, ModelMapper modelMapper, OrdAddressRepository ordAddressRepository) {
        this.cartProductRepository = cartProductRepository;
        this.addressRepository = addressRepository;
        this.cartDAO = cartDAO;
        this.entityManager = entityManager;
        this.ordRepository = ordRepository;
        this.ordProductRepository = ordProductRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.ordAddressRepository = ordAddressRepository;
    }
    //1+n
    @Transactional
    public List<CartProduct> getCart(){
        int customerId = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        List<CartProduct> cartProduct = cartDAO.getPreloadedCartProducts(customerId);
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
    public void confirmOrder(List<CartProduct> cartProducts, Address choosedAddress){
        Session session =entityManager.unwrap(Session.class);
        Customer principal = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer();
        Customer loadedOwner = session.load(Customer.class, principal.getId());

        Ord order =  new Ord();
        //products binding (2 side)
        List<OrdProduct> ordProducts = cartProducts.stream().map(cartProduct ->
                new OrdProduct(order,cartProduct.getProduct(),cartProduct.getQuantity())).collect(Collectors.toList());
        order.setOrdproducts(ordProducts);

        // customer binding (2 side)
        Hibernate.initialize(loadedOwner.getOrds());
        order.setCustomer(loadedOwner);
        loadedOwner.addOrder(order);

        OrdAddress ordAddress = modelMapper.map(choosedAddress,OrdAddress.class);
        ordAddressRepository.save(ordAddress);

        //another info
        order.setOrdAddress(ordAddress);
        order.setOrderedAt(new Date());
        order.setStatus(OrderState.COLLECTING_ORDER);

        ordRepository.save(order);
        ordProductRepository.saveAll(ordProducts);

        //if ok, remove count of bought products from avaible quantity
        cartProducts.stream().forEach(cartProduct -> {
            Product p =cartProduct.getProduct();
            Hibernate.initialize(p);
            p.setQuantity(p.getQuantity()-cartProduct.getQuantity());
            productRepository.save(p);
            cartProductRepository.delete(cartProduct);
        });
    }
}

