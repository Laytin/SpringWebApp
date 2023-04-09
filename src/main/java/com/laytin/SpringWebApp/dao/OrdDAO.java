package com.laytin.SpringWebApp.dao;

import com.laytin.SpringWebApp.models.Ord;
import com.laytin.SpringWebApp.models.OrdProduct;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class OrdDAO {
    private final EntityManager entityManager;
    @Autowired
    public OrdDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
