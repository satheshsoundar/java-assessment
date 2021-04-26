package com.java.assessment.customer.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private Long id;
    String code;
    BigDecimal total;
    BigDecimal totalTax;
    BigDecimal subtotal;
    BigDecimal discounts;
    Long billingAddress;
    Long shippingAddress;
    List<EntryDto> entriesList;
    Long customerId;
    boolean active;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDiscounts() {
        return discounts;
    }

    public void setDiscounts(BigDecimal discounts) {
        this.discounts = discounts;
    }

    public Long getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Long billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Long getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Long shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<EntryDto> getEntriesList() {
        return entriesList;
    }

    public void setEntriesList(List<EntryDto> entriesList) {
        this.entriesList = entriesList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
