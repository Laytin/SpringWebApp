package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //quantity >0 (in stock) and last updated(newer = first)
    //if we search for the specified product by name,we cannot get em here, get it from itemrepo
    //List<Product> findAll(Pageable pageable);
    List<Product> findByNameContainingIgnoreCase(String s, PageRequest of);
    List<Product> findByNameContainingIgnoreCase(String s);
}
