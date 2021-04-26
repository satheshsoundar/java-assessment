package com.java.assessment.customer.repository;

import com.java.assessment.customer.beans.CustomerCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCartRepository extends JpaRepository<CustomerCart, Long> {
}
