package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
