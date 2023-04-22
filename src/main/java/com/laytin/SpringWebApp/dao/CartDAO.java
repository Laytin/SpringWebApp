package com.laytin.SpringWebApp.dao;

import com.laytin.SpringWebApp.models.CartProduct;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class CartDAO {
    private final EntityManager entityManager;
    @Autowired
    public CartDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<CartProduct> getPreloadedCartProducts(int customerId){
        Session session = entityManager.unwrap(Session.class);
        Query q = session.createQuery("select c from CartProduct c JOIN FETCH c.product where c.customer.id="+customerId);
        return q.getResultList();
    }
}
