package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Ord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdRepository extends JpaRepository<Ord, Integer> {
}
