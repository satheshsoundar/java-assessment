package com.java.assessment.cart.service;

import com.java.assessment.cart.exception.CartEntryException;
import com.java.assessment.cart.beans.Cart;
import com.java.assessment.cart.beans.Entry;
import com.java.assessment.cart.dto.EntryDTO;
import com.java.assessment.cart.exception.CartNotFoundException;
import com.java.assessment.cart.exception.EntryNotFoundException;
import com.java.assessment.cart.repository.CartEntryRepository;
import com.java.assessment.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final CartEntryRepository cartEntryRepository;

    private final CartEntryService cartEntryService;

    private final RestTemplate restTemplate;
    @Autowired
    public CartService(CartRepository cartRepository,
                       CartEntryRepository cartEntryRepository,
                       CartEntryService cartEntryService,
                       RestTemplate restTemplate) {
       this.cartRepository = cartRepository;
       this.cartEntryRepository = cartEntryRepository;
       this.cartEntryService = cartEntryService;
        this.restTemplate = restTemplate;
    }

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Optional<Cart> getCartByCode(String code) {
        return cartRepository.findCartByCode(code);
    }

    public Optional<Entry> updateItemInCart(EntryDTO entryDTO) throws EntryNotFoundException {
        Optional<Entry> entry =  cartEntryService.updateItemInCart(entryDTO);
        recalculateCart(entry.get().getCart(), entry.get().getCart().getEntriesList());
        saveCart(entry.get().getCart());
        return entry;
    }

    public Optional<Entry> deleteItemInCart(EntryDTO entryDTO) throws EntryNotFoundException {
        Optional<Entry> entry =  cartEntryService.updateItemInCart(entryDTO);
        recalculateCart(entry.get().getCart(), entry.get().getCart().getEntriesList());
        saveCart(entry.get().getCart());
        return entry;
    }

    public Optional<Cart> addItemToCart(EntryDTO entryDTO) throws CartEntryException {
        if(cartEntryService.isItemAlreadyInCart(entryDTO)) {
            throw new CartEntryException("Entry Already Present");
        }
        Cart cart = entryDTO.getCart();
        List<Entry> entries = cart.getEntriesList();
        entries.add(cartEntryService.addItemInCart(entryDTO));
        cart.setEntriesList(entries);
        recalculateCart(cart, entries);
        saveCart(cart);
        return Optional.of(cart);
    }

    public void recalculateCart(Cart cart, List<Entry> entries) {
        BigDecimal total = entries.stream().map(e-> e.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        total = total.setScale(2, RoundingMode.HALF_UP);
        cart.setTotal(total);
        cart.setSubtotal(total);
        cart.setDiscounts(new BigDecimal(0));
        BigDecimal tax = total.multiply(new BigDecimal(0.19)).setScale(2, RoundingMode.HALF_UP);
        cart.setTotalTax(tax);
    }


    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }


    public Optional<Cart> getCartByCustomerId(Long customerId) {
        return cartRepository.findCartByCustomerIdAndStatus(customerId, true);
    }

    public String getCartCodeByCustomerId(Long customerId) {
        return createNewCart(customerId).getCode();
    }

    public Cart createNewCart(Long customerId){
        Optional<Cart> cartOptional = cartRepository.findCartByCustomerIdAndStatus(customerId, true);
        if(cartOptional.isPresent()) {
            return cartOptional.get();
        }
        Cart cart = new Cart();
        cart.setCode("cart-"+ UUID.randomUUID());
        cart.setCustomerId(customerId);
        cart.setActive(true);
        String customerBaseURL = "http://kong-gateway:8000/customer-service/customer/"+customerId+"/address/";
        Long billingAddress = restTemplate.getForObject(customerBaseURL+"billing", Long.class);
        Long shippingAddress = restTemplate.getForObject(customerBaseURL+"shipping", Long.class);
        cart.setBillingAddress(billingAddress);
        cart.setShippingAddress(shippingAddress);
        System.out.println("Saving Cart" + cart.getCode());
        saveCart(cart);
        return cart;
    }

    public Cart checkoutCart(Cart cartOld) throws CartNotFoundException {
        Optional<Cart> cartOptional = cartRepository.findCartByCode(cartOld.getCode());
        if (!cartOptional.isPresent()) {
            throw new CartNotFoundException("Cart Not Found");
        }
        Cart cart = cartOptional.get();
        cart.setActive(false);
        return saveCart(cart);
    }
}
