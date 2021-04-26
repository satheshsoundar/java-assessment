package com.java.assessment.cart.repository;


import com.java.assessment.cart.beans.Cart;
import com.java.assessment.cart.beans.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartEntryRepository extends JpaRepository<Entry,Long> {

    @Query("SELECT c FROM Entry c where c.code = :code")
    Optional<Entry> findCartEntryByCode(@Param("code") String code);
}
