package com.java.assessment.customer.controller;


import com.java.assessment.customer.dto.CustomerCartDto;
import com.java.assessment.customer.dto.EntryDto;
import com.java.assessment.customer.exception.CartNotFoundException;
import com.java.assessment.customer.service.CustomerCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerCartController {


    private final CustomerCartService customerCartService;

    @Autowired
    public CustomerCartController(CustomerCartService customerCartService) {
        this.customerCartService = customerCartService;
    }

    @GetMapping("/customer/cart/{id}")
    public CustomerCartDto getCustomerCart(@PathVariable("id") Long id) throws CartNotFoundException {
        return customerCartService.getCustomerCart( id );
    }

    @GetMapping("/customer/carts")
    public List<CustomerCartDto> getAllCustomerCart(){
        return customerCartService.getAllCustomerCart();
    }

    @PostMapping("/customer/cart/{customer-id}/product")
    public CustomerCartDto addProductToCart(@PathVariable("customer-id") Long customerId, @RequestBody EntryDto entryDto) throws CartNotFoundException {
        return customerCartService.addProductToCart( customerId, entryDto );
    }

    @PutMapping("/customer/cart/{customer-id}/product")
    public EntryDto updateProductToCart(@PathVariable("customer-id") Long customerId, @RequestBody EntryDto entryDto) throws CartNotFoundException {
        return customerCartService.updateProductToCart( customerId, entryDto );
    }
}
