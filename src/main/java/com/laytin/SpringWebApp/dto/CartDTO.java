package com.laytin.SpringWebApp.dto;

import com.laytin.SpringWebApp.models.CartProduct;

import java.util.List;

public class CartDTO {
    private List<CartProduct> cartproducts; //one cart many products
    private int total;

    public CartDTO() {
    }
    public CartDTO(List<CartProduct> cartproducts) {
        this.cartproducts = cartproducts;
    }

    public List<CartProduct> getCartproducts() {
        return cartproducts;
    }

    public void setCartproducts(List<CartProduct> cartproducts) {
        this.cartproducts = cartproducts;
    }

    public int getTotal() {
        return total;
    }
    public void calculateTotal(){
        cartproducts.forEach(p -> total += (p.getProduct().getPrice()*p.getQuantity()));
    }
}
