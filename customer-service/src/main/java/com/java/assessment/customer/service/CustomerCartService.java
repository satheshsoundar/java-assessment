package com.java.assessment.customer.service;

import com.java.assessment.customer.beans.CustomerCart;
import com.java.assessment.customer.dto.CartDto;
import com.java.assessment.customer.dto.CustomerCartDto;
import com.java.assessment.customer.dto.EntryDto;
import com.java.assessment.customer.dto.ProductDto;
import com.java.assessment.customer.exception.CartNotFoundException;
import com.java.assessment.customer.repository.CustomerCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerCartService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerCartRepository customerCartRepository;

    @Autowired
    private AddressService addressService;

    public CustomerCartDto getCustomerCart(Long customerId ) throws CartNotFoundException {

        CartDto cartDto = restTemplate.getForObject("http://kong-gateway:8000/cart-service/api/carts/customer/" + customerId
                , CartDto.class);
        if (cartDto == null) {
            throw new CartNotFoundException("Active Cart Not Found");
        }

        return mapCustomerCartDto(customerId, cartDto);
    }

    private CustomerCartDto mapCustomerCartDto(Long customerId, CartDto cartDto){
        CustomerCartDto customerCartDto = mapCartToCustomerCart( cartDto );
        return customerCartDto;
    }

    private void updateAddressInCustomerCartDto(CartDto cartDto, CustomerCartDto customerCartDto) {
        customerCartDto.setBillingAddress(addressService.getAddressById(cartDto.getBillingAddress()));
        customerCartDto.setShippingAddress(addressService.getAddressById(cartDto.getShippingAddress()));
    }

    private CustomerCartDto mapCartToCustomerCart(CartDto cartDto) {
        CustomerCartDto customerCartDto = new CustomerCartDto();
        customerCartDto.setCustomerId(cartDto.getCustomerId());
        customerCartDto.setCartCode(cartDto.getCode());
        customerCartDto.setSubtotal(cartDto.getSubtotal());
        customerCartDto.setTotal(cartDto.getTotal());
        customerCartDto.setTotalTax(cartDto.getTotalTax());
        customerCartDto.setEntriesList(cartDto.getEntriesList());
        customerCartDto.setActive(cartDto.isActive());
        updateAddressInCustomerCartDto(cartDto, customerCartDto);
        return customerCartDto;
    }

    public List<CustomerCartDto> getAllCustomerCart(){
        CartDto[] cartDtos = restTemplate.getForObject("http://kong-gateway:8000/cart-service/api/carts/"
                ,CartDto[].class);
        List<CartDto> cartDtoList = Arrays.asList(cartDtos);

        return updateCartDtosToCustomerCartList(cartDtoList);
    }

    private List<CustomerCartDto> updateCartDtosToCustomerCartList(List<CartDto> cartDtoList) {
        return cartDtoList.stream()
                .map(cartDto -> mapCartToCustomerCart(cartDto))
                    .collect(Collectors.toList());
    }

    public CustomerCart saveCustomerCart( CustomerCart customerCart ) {
        return customerCartRepository.save( customerCart );
    }

    public CustomerCartDto addProductToCart(Long customerId, EntryDto entryDto) throws CartNotFoundException {

        ProductDto productDto = restTemplate.getForObject("http://kong-gateway:8000/product-service/product/code/" + entryDto.getCode()
                , ProductDto.class);

        entryDto.setPrice(productDto.getPrice());

        String cartCode = restTemplate.
                getForObject("http://kong-gateway:8000/cart-service/api/carts/customer/"+customerId+"/cart-code"
                , String.class);

         CartDto cartDto = restTemplate.postForObject("http://kong-gateway:8000/cart-service/api/carts/"+ cartCode + "/entries"
                , entryDto, CartDto.class);
        if (cartDto == null) {
            throw new CartNotFoundException("Active Cart Not Found");
        }
        CustomerCartDto customerCartDto = mapCartToCustomerCart( cartDto );
        updateAddressInCustomerCartDto(cartDto, customerCartDto);
        return customerCartDto;
    }

    public EntryDto updateProductToCart(Long customerId, EntryDto entryDto) throws CartNotFoundException {

        ProductDto productDto = restTemplate.getForObject("http://kong-gateway:8000/product-service/product/code/" + entryDto.getCode()
                , ProductDto.class);

        entryDto.setPrice(productDto.getPrice());

        String cartCode = restTemplate.
                getForObject("http://kong-gateway:8000/cart-service/api/carts/customer/"+customerId+"/cart-code"
                        , String.class);
        Map<String, String > params = new HashMap<>();
        params.put("cartCode", cartCode);
        restTemplate.put("http://kong-gateway:8000/cart-service/api/carts/{cartCode}/entries"
                , entryDto, params);
        return entryDto;
    }

    public EntryDto deleteProductInCart(Long customerId, EntryDto entryDto) throws CartNotFoundException {

        CartDto cartDto = restTemplate.
                getForObject("http://kong-gateway:8000/cart-service/api/carts/customer/"+customerId
                        , CartDto.class);

        if (cartDto == null) {
            throw new CartNotFoundException("Active Cart Not Found");
        }

        restTemplate.delete("http://kong-gateway:8000/cart-service/api/carts/"+ cartDto.getCode() + "/entries"
                , entryDto);
        return entryDto;
    }


    public CartDto checkoutCart(Long customerId) throws CartNotFoundException {
        CartDto cartDto = restTemplate.
                getForObject("http://kong-gateway:8000/cart-service/api/carts/customer/"+customerId
                        , CartDto.class);

        if (cartDto == null) {
            throw new CartNotFoundException("Active Cart Not Found");
        }
        cartDto.setActive(true);

        Map<String, String > params = new HashMap<>();
        restTemplate.put("http://kong-gateway:8000/cart-service/api/carts/checkout", cartDto, params);
        return cartDto;
    }
}
