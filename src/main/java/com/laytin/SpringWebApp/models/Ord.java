package com.laytin.SpringWebApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "ord")
public class Ord {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "ord")
    private List<OrdProduct> ordproducts; //one order many orderProducts

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name="address_id", referencedColumnName="id")
    private Address address;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private OrderState status;

    @NotEmpty
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderedAt;

    @Transient
    private int total;

    public Ord() {
    }

    public Ord(List<OrdProduct> ordproducts, Customer customer, Address address, OrderState status, LocalDateTime orderedAt, int total) {
        this.ordproducts = ordproducts;
        this.customer = customer;
        this.address = address;
        this.status = status;
        this.orderedAt = orderedAt;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrdProduct> getOrdproducts() {
        return ordproducts;
    }

    public void setOrdproducts(List<OrdProduct> ordproducts) {
        this.ordproducts = ordproducts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderState getStatus() {
        return status;
    }

    public void setStatus(OrderState status) {
        this.status = status;
    }

    public LocalDateTime  getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime  orderedAt) {
        this.orderedAt = orderedAt;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public void calculateTotal(){
        total=0;
        ordproducts.stream().forEach(ordProduct -> {total+=ordProduct.getProduct().getPrice()*ordProduct.getQuantity();});
    }
}
