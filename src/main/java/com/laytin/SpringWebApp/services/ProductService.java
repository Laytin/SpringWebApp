package com.laytin.SpringWebApp.services;

import com.laytin.SpringWebApp.dao.ProductDAO;
import com.laytin.SpringWebApp.models.Product;
import com.laytin.SpringWebApp.repositories.ItemRepository;
import com.laytin.SpringWebApp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, ItemRepository itemRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }
    ////////////////////////////////////////////////////////////////
    public List<Product> getProductList(int page,int perpage){
        return productRepository.findAll( PageRequest.of(page,perpage)).getContent();
    }
    public List<Product> getProductListSortBy(int page,int perpage, String sortBy){
        return productRepository.findAll( PageRequest.of(page,perpage, Sort.by(sortBy))).getContent();
    }

    public List<Product> searchProducts(String searchQuest, int page,int perpage){
        return null;//productDAO.getSearchProducts(searchQuest,page,perpage);
    }
    public List<Product> searchProductsSortBy(String searchQuest, int page,int perpage, String sortBy){
        return null;//productDAO.getSearchProducts(searchQuest,page,perpage);
    }

    //get product list
    //get sorted
    //search (do it with item repo)
}
