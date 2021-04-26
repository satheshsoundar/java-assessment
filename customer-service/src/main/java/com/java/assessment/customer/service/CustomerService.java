package com.java.assessment.customer.service;

import com.java.assessment.customer.beans.Customer;
import com.java.assessment.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(  Customer customer ){
        return customerRepository.save( customer );
    }

    public Long getAddressIdByType(Long customerId, String type) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            if ("billing".equalsIgnoreCase(type)) {
                return customerOptional.get().getBillingAddress().getId();
            } else if ("shipping".equalsIgnoreCase(type)) {
                return customerOptional.get().getShippingAddress().getId();
            }
        }
        return null;
    }
}
