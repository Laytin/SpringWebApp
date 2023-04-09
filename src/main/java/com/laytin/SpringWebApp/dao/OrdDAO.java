package com.laytin.SpringWebApp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class OrdDAO {
    private final EntityManager entityManager;
    @Autowired
    public OrdDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
