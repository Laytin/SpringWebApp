package com.laytin.SpringWebApp.models;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "cart")
public class Cart implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer; // one user one cart

    @OneToMany(mappedBy = "cart")
    private List<CartProduct> cartproducts; //one cart many products
    @Transient
    private int total;

    public Cart() {
    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartProduct> getCartproducts() {
        return cartproducts;
    }

    public void setCartproducts(List<CartProduct> cartproducts) {
        this.cartproducts = cartproducts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void addToCart(CartProduct product){
        cartproducts.add(product);
    }
    public void removeFrom(CartProduct product){
        cartproducts.remove(product);
    }
    public void calculateTotal(){
        total  = 0;
        cartproducts.forEach(p -> total += (p.getProduct().getPrice()*p.getQuantity()));
    }
    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customer=" + customer +
                ", cartproducts=" + cartproducts +
                ", total=" + total +
                '}';
    }

}
