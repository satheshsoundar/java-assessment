package com.java.assessment.customer.controller;

import com.java.assessment.customer.beans.Customer;
import com.java.assessment.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }
    @GetMapping("/customer/{customerId}/address/{type}")
    public Long getCustomersAddress(@PathVariable Long customerId, @PathVariable String type) {
        return customerService.getAddressIdByType(customerId, type);
    }
}
