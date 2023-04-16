package com.laytin.SpringWebApp.dao;

import com.laytin.SpringWebApp.models.Customer;
import com.laytin.SpringWebApp.models.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDAO {
    @PersistenceContext
    private final EntityManager entityManager;
    @Autowired
    public ProductDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional(readOnly = true)
    public List<Product> getProductListSearch(String searchRequest, PageRequest pr) {
        String[] sort = pr.getSort().toString().split(":"); //cringe, i'l rewrite this
        Session session = entityManager.unwrap(Session.class);

//        select item.id, item.name,p.color From item
//        INNER JOIN product p on item.id = p.item_id
//        Where item.name LIKE 'Nike%'
//        group by item.id,p.color

/*        Query query = session.createQuery("select i.id, i.name,Product.color," +
                        "MAX(Product.id) as prodid, " +
                        " MAX(Product.price) as prodprice" +
                        " from Item i" +
                        " inner join Product.item" + //mb use one side join?
                        " where upper(i.name) Like upper(?) " + //avoid case-sensitive search
                        " group by i.id,Product.color"
                //" order by prod" + sort[0].toLowerCase() + " " + sort[1]
        ); //id or name*/
        return null;
    }
}
