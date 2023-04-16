package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.repositories.CartProductRepository;
import com.laytin.SpringWebApp.repositories.ProductRepository;
import com.laytin.SpringWebApp.security.CustomerDetails;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

    @PersistenceContext
    private final EntityManager entityManager;
    @Autowired
    public ProductService(ProductRepository productRepository, CartProductRepository cartProductRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.entityManager = entityManager;
    }
    ////////////////////////////////////////////////////////////////
    //fixed 1+n
    public List<Product> getProducts(int page,String sort,String dir){
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Product> productList = productRepository.findAll(PageRequest.of(page-1, 10, Sort.by(direction,sort.toLowerCase()))).getContent();
        productList.stream().forEach(product -> {
            product.setImageURLs(getImages(product.getId()));
        });
        return productList;
    }
    public List<Product> getProducts(int page,String sort,String dir,String search){
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Product> productList = productRepository.findByNameContainingIgnoreCase(search,PageRequest.of(page-1, 10, Sort.by(direction,sort.toLowerCase())));
        productList.stream().forEach(product -> {
            product.setImageURLs(getImages(product.getId()));
        });
        return null;
    }
    public Product getProduct(int id) {
        Product res = productRepository.findById(id).orElse(null);
        res.setImageURLs(getImages(id));
        return res;
    }
    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    private List<String> getImages(int id){
        List<String> result = new ArrayList<>(Collections.singletonList(
                "https://www.shutterstock.com/image-vector/default-image-icon-thin-linear-260nw-2136460353.jpg"
        ));
        return result;
    }
    @Transactional
    public void addProductToCart(CartProduct cartProduct) {
        //select * from product where product.name = (select name from product where product.id=2)
        // TODO: optimize
        int customerId = ((CustomerDetails) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal()).getCustomer().getId();
        Session session = entityManager.unwrap(Session.class);
        Customer customer = session.load(Customer.class,customerId);
        cartProduct.setCustomer(customer);
        cartProductRepository.save(cartProduct);
    }
}
