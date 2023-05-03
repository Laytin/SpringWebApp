package com.laytin.SpringWebApp.models;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
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
    @Column(name = "fullname")
    private String fullname;

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

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<CartProduct> cartproducts;

    @Column(name = "customerrole")
    @Enumerated(EnumType.STRING)
    private CustomerRole customer_Role;
    public Customer() {
    }

    public Customer(String username, String fullname, String password, String email, CustomerRole customerRole) {
        this.username = username;
        this.fullname = fullname;
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


    public CustomerRole getCustomer_Role() {
        return customer_Role;
    }

    public void setCustomer_Role(CustomerRole customer_Role) {
        this.customer_Role = customer_Role;
    }

    public List<CartProduct> getCartproducts() {
        return cartproducts;
    }

    public void setCartproducts(List<CartProduct> cartproducts) {
        this.cartproducts = cartproducts;
    }
    public void addOrder(Ord order){this.ords.add(order);}
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
                ", cartproducts=" + cartproducts +
                ", customer_Role=" + customer_Role +
                '}';
    }
}
