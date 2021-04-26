package com.java.assessment.cart.controller;

import com.java.assessment.cart.exception.CartEntryException;
import com.java.assessment.cart.exception.CartNotFoundException;
import com.java.assessment.cart.beans.Cart;
import com.java.assessment.cart.beans.Entry;
import com.java.assessment.cart.dto.EntryDTO;
import com.java.assessment.cart.exception.EntryNotFoundException;
import com.java.assessment.cart.service.CartEntryService;
import com.java.assessment.cart.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private CartService cartService;

    private CartEntryService cartEntryService;

    @Autowired
    public CartController(CartService cartService,CartEntryService cartEntryService) {
        this.cartService = cartService;
        this.cartEntryService = cartEntryService;
    }

    @GetMapping("/api/carts/{code}")
    public Optional<Cart> findCartById(@PathVariable String code) {
        return cartService.getCartByCode(code);
    }

    @GetMapping("/api/carts/customer/{customerId}")
    public Optional<Cart> findCartByCustomerId(@PathVariable Long customerId) {
        return cartService.getCartByCustomerId(customerId);
    }

    @GetMapping("/api/carts/customer/{customerId}/cart-code")
    public String findCartCodeByCustomerId(@PathVariable Long customerId) {
        return cartService.getCartCodeByCustomerId(customerId);
    }

    @GetMapping("/api/carts")
    public List<Cart> findAllCarts() {
        return cartService.getCarts();
    }

    @PostMapping("/api/carts/customer/{customerId}/create/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Cart> createNewCart(@PathVariable Long customerId) {
        return Optional.of(cartService.createNewCart(customerId));
    }

    @PostMapping("/api/carts/{code}/entries")
    public Optional<Cart> addItemToCart(@PathVariable String code, @RequestBody EntryDTO entryDTO)
        throws CartNotFoundException, CartEntryException {
        getAndPrepareCart(code, entryDTO);
        return cartService.addItemToCart(entryDTO);

    }

    @PutMapping("/api/carts/{code}/entries")
    public Optional<Entry> updateItem(@PathVariable String code, @RequestBody EntryDTO entryDTO)
        throws CartNotFoundException, EntryNotFoundException {
        getAndPrepareCart(code, entryDTO);
        return cartService.updateItemInCart(entryDTO);
    }


    private void getAndPrepareCart(@PathVariable String code,
                                   @RequestBody EntryDTO entryDTO) throws CartNotFoundException {
        Optional<Cart> cart = cartService.getCartByCode(code);
        if (!cart.isPresent()) {
            throw new CartNotFoundException("Cart Not Found");
        }
        entryDTO.setCart(cart.get());
    }

}

