package com.laytin.SpringWebApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;


@Entity
@Table(name ="Product")
public class Product {
    // Item is a single thing like "iphone 14"
    // Different variable of this item is products
    // "Iphone 14" is an item, "Blue XS" is a product
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="item_id",referencedColumnName = "id")
    private Item item;

    @NotEmpty
    @Column(name="color")
    private String color;

    @NotEmpty
    @Column(name="color")
    private String size;

    @NotEmpty
    @Column(name="color")
    private int price;

    @Transient
    private List<String> imageURLs;

    public Product() {
    }

    public Product(Item item, String color, String size, int price, List<String> imageURLs) {
        this.item = item;
        this.color = color;
        this.size = size;
        this.price = price;
        this.imageURLs = imageURLs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(List<String> imageURLs) {
        this.imageURLs = imageURLs;
    }
}
