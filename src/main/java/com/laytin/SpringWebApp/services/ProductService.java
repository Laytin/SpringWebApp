package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.ProductDAO;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDAO productDAO;
    @Autowired
    public ProductService(ProductRepository productRepository, ProductDAO productDAO) {
        this.productRepository = productRepository;
        this.productDAO = productDAO;
    }
    ////////////////////////////////////////////////////////////////
    //fixed 1+n
    public List<Product> getProducts(int page,String sort,String dir){
        System.out.println(dir);
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Product> productList = productRepository.findAll(PageRequest.of(page-1, 10, Sort.by(direction,sort.toLowerCase()))).getContent();

        productList.stream().forEach(product -> {
            product.setImageURLs(getImages(product.getId()));
        });
        return productList;
    }
    public List<Product> getProducts(int page,String sort,String dir,String search){
        Sort.Direction direction = dir.toLowerCase().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        List<Product> products = new ArrayList<Product>();

        products.stream().forEach(product -> {
            product.setImageURLs(getImages(product.getId()));
        });
        return null;
    }

    private List<String> getImages(int id){
        List<String> result = new ArrayList<>(Collections.singletonList(
                "https://www.shutterstock.com/image-vector/default-image-icon-thin-linear-260nw-2136460353.jpg"
        ));
        return result;
    }
}
