package com.laytin.SpringWebApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(name = "username")
    private String username;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Column(name = "email")
    @Email
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses; // one user many addresses

    @OneToMany(mappedBy = "customer")
    private List<Ord> ords; //one user many orders

    @OneToOne(mappedBy = "customer")
    private Cart cart; // one user one cart

    public Customer() {
    }

    public Customer(String username, String password, String email, List<Address> addresses, List<Ord> ords, Cart cart) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.addresses = addresses;
        this.ords = ords;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Ord> getOrds() {
        return ords;
    }

    public void setOrds(List<Ord> ords) {
        this.ords = ords;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
