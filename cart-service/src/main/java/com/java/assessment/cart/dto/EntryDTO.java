package com.java.assessment.cart.dto;

import com.java.assessment.cart.beans.Cart;

import java.math.BigDecimal;

// this is the entrydto
public class EntryDTO {
    private String code;
    private Long quantity;
    private BigDecimal price;
    private Cart cart;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
