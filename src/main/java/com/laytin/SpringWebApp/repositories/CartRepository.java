package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.Cart;
import com.laytin.SpringWebApp.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
