package com.laytin.SpringWebApp.repositories;

import com.laytin.SpringWebApp.models.CartProduct;
import com.laytin.SpringWebApp.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
