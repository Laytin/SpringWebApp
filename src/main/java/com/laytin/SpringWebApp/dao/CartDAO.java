package com.laytin.SpringWebApp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class CartDAO {
    private final EntityManager entityManager;
    @Autowired
    public CartDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
