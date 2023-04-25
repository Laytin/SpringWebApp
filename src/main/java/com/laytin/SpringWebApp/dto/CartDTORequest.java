package com.laytin.SpringWebApp.dto;

import com.laytin.SpringWebApp.models.Address;
import com.laytin.SpringWebApp.models.CartProduct;

import java.io.Serializable;
import java.util.List;

public class CartDTORequest implements Serializable {
    private List<CartProduct> productList;
    private Address address;

    public CartDTORequest() {
    }

    public CartDTORequest(List<CartProduct> productList, Address address) {
        this.productList = productList;
        this.address = address;
    }

    public CartDTORequest(List<CartProduct> productList) {
        this.productList = productList;
    }

    public List<CartProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<CartProduct> productList) {
        this.productList = productList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
