package com.java.assessment.customer.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_billing_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Address billingAddress;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_shipping_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Address shippingAddress;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    @JsonManagedReference
    List<Address> addressList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
