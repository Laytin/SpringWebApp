package com.laytin.SpringWebApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    public List<String> getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(List<String> imageURLs) {
        this.imageURLs = imageURLs;
    }

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

    public void removeQuantity(int i){
        this.quantity=-i;
    }
    public void addQuantity(int i){
        this.quantity=+i;
    }
    public void loadImages(){
        imageURLs = new ArrayList<>();
        try {
                List<String> f = Files.list(Paths.get("uploads/products/" +getId()+"/"))
                        .filter(file -> !Files.isDirectory(file))
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());
            if(f.isEmpty()){
                imageURLs.add(new File("uploads/products/default").listFiles()[0].getPath());
            }else{
                f.forEach(file-> imageURLs.add("uploads/products/" + id + "/" + file.replaceAll("[\\[*?\\]]*","")));
            }
        } catch (IOException e) {
            imageURLs.add("https://thumbs.dreamstime.com/b/no-image-available-icon-photo-camera-flat-vector-illustration-132483141.jpg");
        }
    }

}
