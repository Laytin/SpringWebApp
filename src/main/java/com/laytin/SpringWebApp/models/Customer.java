package com.laytin.SpringWebApp.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {


    private static final long serialVersionUID = 1L;
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
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Address> addresses; // one user many addresses

    @OneToMany(mappedBy = "customer")
    private List<Ord> ords; //one user many orders


    @OneToOne(mappedBy = "customer")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Cart cart; // one user one cart

    @Column(name = "customer_Role")
    @Enumerated(EnumType.STRING)
    private CustomerRole customer_Role;
    public Customer() {
    }

    public Customer(String username, String password, String email, CustomerRole customerRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.customer_Role = customerRole;
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
    public void addAddress(Address address) {
        this.addresses.add(address);
    }
    public void removeAddress(Address address) {
        this.addresses.remove(address);
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

    public CustomerRole getCustomer_Role() {
        return customer_Role;
    }

    public void setCustomer_Role(CustomerRole customer_Role) {
        this.customer_Role = customer_Role;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", addresses=" + addresses +
                ", ords=" + ords +
                ", cart=" + cart +
                ", customer_Role=" + customer_Role +
                '}';
    }


}
