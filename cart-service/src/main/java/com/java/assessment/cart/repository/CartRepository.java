package com.java.assessment.cart.repository;

import com.java.assessment.cart.beans.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c where c.code = :code")
    Optional<Cart> findCartByCode(@Param("code") String code);

    @Query("SELECT c FROM Cart c where c.customerId = :customerId AND c.active = :active")
    Optional<Cart> findCartByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("active") boolean active);
}
