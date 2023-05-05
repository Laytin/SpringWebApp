package com.laytin.SpringWebApp.models;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name="ordaddress_id", referencedColumnName="id")
    private OrdAddress ordAddress;

    @Enumerated(EnumType.STRING)
    private OrderState status;
    
    @Column(name = "orderedat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderedAt;

    @Transient
    private int total;

    public Ord() {
    }

    public Ord(List<OrdProduct> ordproducts, Customer customer, OrdAddress ordAddress, OrderState status, Date orderedAt, int total) {
        this.ordproducts = ordproducts;
        this.customer = customer;
        this.ordAddress = ordAddress;
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

    public OrdAddress getAddress() {
        return ordAddress;
    }

    public void setOrdAddress(OrdAddress ordAddress) {
        this.ordAddress = ordAddress;
    }

    public OrderState getStatus() {
        return status;
    }

    public void setStatus(OrderState status) {
        this.status = status;
    }

    public Date  getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date  orderedAt) {
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
