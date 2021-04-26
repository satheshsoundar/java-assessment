package com.java.assessment.customer.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerCart {
    @Id
    private Long customerId;
    private String cartCode;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCartCode() {
        return cartCode;
    }

    public void setCartCode(String cartCode) {
        this.cartCode = cartCode;
    }
}
