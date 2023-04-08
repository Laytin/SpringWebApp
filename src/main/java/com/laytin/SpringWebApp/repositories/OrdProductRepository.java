package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.OrdProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdProductRepository extends JpaRepository<OrdProduct, Integer> {
}
