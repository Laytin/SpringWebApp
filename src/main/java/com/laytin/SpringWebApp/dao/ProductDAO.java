package com.laytin.SpringWebApp.dao;

import com.laytin.SpringWebApp.models.Product;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class ProductDAO {
/*    private final EntityManager entityManager;
    @Autowired
    public ProductDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<Product> getSearchProducts(String searchRequest, int page, int perPage){
        int start = 0+(page-1)*perPage;
        int end = perPage*page;
        Session session = entityManager.unwrap(Session.class);
        List<Product> result  = session.createSQLQuery("SELECT " +
                "(SELECT product.color,product.item_id FROM product p GROUP BY product.color,product.item_id) as productGroupSizes, " +
                "JOIN ( Item on item_id = item.id)" +
                "where item.name Like 'Nike%'\n").setFirstResult(start).setMaxResults(end).list();
        return result;
    }*/
}
