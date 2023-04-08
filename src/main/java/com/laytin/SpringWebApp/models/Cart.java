package com.laytin.SpringWebApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer; // one user one cart

    @OneToMany(mappedBy = "cart")
    private List<CartProduct> cartproducts; //one cart many products

    @Transient
    private int total;

    public Cart() {
    }

    public Cart(Customer customer, List<CartProduct> cartproducts, int total) {
        this.customer = customer;
        this.cartproducts = cartproducts;
        this.total = total;
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
}
