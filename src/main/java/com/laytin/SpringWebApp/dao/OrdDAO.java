package com.laytin.SpringWebApp.dao;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Ord;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class OrdDAO {
    private final EntityManager entityManager;
    @Autowired
    public OrdDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional(readOnly = true)
    public List<Ord> getOrders(int id,  Pageable pageable){
        Session session = entityManager.unwrap(Session.class);
        int start = pageable.getPageNumber()* pageable.getPageSize();
        int end = (pageable.getPageNumber()+1)* pageable.getPageSize();
        Query q = session.createQuery("select o from Ord o JOIN FETCH o.ordproducts op JOIN FETCH op.product WHERE o.customer=?1")
                .setParameter(1,session.load(Customer.class,id)).setFirstResult(start).setMaxResults(end);
        return q.getResultList();
    }
}
