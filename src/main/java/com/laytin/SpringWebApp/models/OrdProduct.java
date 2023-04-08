package com.laytin.SpringWebApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "ordproduct")
public class OrdProduct {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ord_id",referencedColumnName = "id")
    private Ord ord; //many products one order

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @NotEmpty
    @Column(name="quantity")
    private int quantity;

    public OrdProduct() {
    }

    public OrdProduct(Ord ord, Product product, int quantity) {
        this.ord = ord;
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ord getOrd() {
        return ord;
    }

    public void setOrd(Ord ord) {
        this.ord = ord;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
