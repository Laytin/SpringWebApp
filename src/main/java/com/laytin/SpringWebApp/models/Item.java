package com.laytin.SpringWebApp.models;

import java.util.List;

public class Item {
    // Item is a single thing like "iphone 14"
    // Different variable of this item is products
    // "Iphone 14" is an item, "Blue XS" is a product


    private int id;
    private String name;
    private List<Item> products;
}
