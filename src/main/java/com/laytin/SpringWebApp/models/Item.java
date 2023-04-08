package com.laytin.SpringWebApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
@Entity
@Table(name = "item")
public class Item {

    // Item is a single thing like "iphone 14"
    // Different variable of this item is products
    // "Iphone 14" is an item, "Blue XS" is a product

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy ="item")
    private List<Product> products;

    public Item() {
    }

    public Item(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
