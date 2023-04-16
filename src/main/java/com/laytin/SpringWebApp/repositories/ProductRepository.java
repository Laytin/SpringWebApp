package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //quantity >0 (in stock) and last updated(newer = first)
    //if we search for the specified product by name,we cannot get em here, get it from itemrepo
    //List<Product> findAll(Pageable pageable);
}
