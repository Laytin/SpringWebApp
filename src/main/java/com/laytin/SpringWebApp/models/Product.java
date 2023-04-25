package com.laytin.SpringWebApp.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name ="Product")
public class Product implements Serializable {
    // Item is a single thing like "iphone 14"
    // Different variable of this item is products
    // "Iphone 14" is an item, "Blue XS" is a product
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name="color")
    private String color;

    @Column(name="price")
    private int price;

    @Column(name="quantity")
    private int quantity;

    @Transient
    private List<String> imageURLs;

    public Product() {
    }

    public Product(String name, String color, int price, int quantity) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(List<String> imageURLs) {
        this.imageURLs = imageURLs;
    }
    public void removeQuantity(int i){
        this.quantity=-i;
    }
    public void addQuantity(int i){
        this.quantity=+i;
    }
}
