package com.java.assessment.cart;

import com.java.assessment.cart.beans.Cart;
import com.java.assessment.cart.beans.Entry;
import com.java.assessment.cart.service.CartEntryService;
import com.java.assessment.cart.service.CartService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;

@ComponentScan("com.java.assessment.cart.*")
@SpringBootApplication
@EntityScan("com.java.assessment.cart.*")
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = { "com.java.assessment.cart.*" })
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	@Bean
	ApplicationRunner init(CartService cartService ,CartEntryService cartEntryService) {
		Cart cart1 = new Cart();
		cart1.setCode("cart-1234");
		cart1.setTotal(new BigDecimal(50));
		cart1.setSubtotal(new BigDecimal(48));
		cart1.setTotalTax(new BigDecimal(8));
		cart1.setBillingAddress(1l);
		cart1.setShippingAddress(1l);
		cart1.setCustomerId(1l);
		cart1.setActive(true);

		Entry entry1 = new Entry();
		entry1.setCode("Product-1234");
		entry1.setQuantity(10L);
		entry1.setTotal(BigDecimal.valueOf(25));

		Entry entry2 = new Entry();
		entry2.setCode("Product-2345");
		entry2.setQuantity(1L);
		entry2.setTotal(BigDecimal.valueOf(23));




		Cart cart2 = new Cart();
		cart2.setCode("cart-12344");
		cart2.setTotal(new BigDecimal(25));
		cart2.setSubtotal(new BigDecimal(25));
		cart2.setTotalTax(new BigDecimal(5));
		cart2.setBillingAddress(2l);
		cart2.setShippingAddress(2l);
		cart2.setCustomerId(2l);
		cart2.setActive(true);

		Entry entry3 = new Entry();
		entry3.setCode("Product-3456");
		entry3.setQuantity(10L);
		entry3.setTotal(BigDecimal.valueOf(5));

		Entry entry4 = new Entry();
		entry4.setCode("Product-5678");
		entry4.setQuantity(1L);
		entry4.setTotal(BigDecimal.valueOf(20));

		cartEntryService.saveCartEntry(entry1);
		cartEntryService.saveCartEntry(entry2);
		cartEntryService.saveCartEntry(entry3);
		cartEntryService.saveCartEntry(entry4);


		cart1.setEntriesList(Arrays.asList(entry1,entry2));
		cart2.setEntriesList(Arrays.asList(entry3,entry4));

		cartService.saveCart(cart1);
		cartService.saveCart(cart2);


		entry1.setCart(cart1);
		entry2.setCart(cart1);
		entry3.setCart(cart2);
		entry4.setCart(cart2);


		cartEntryService.saveCartEntry(entry1);
		cartEntryService.saveCartEntry(entry2);
		cartEntryService.saveCartEntry(entry3);
		cartEntryService.saveCartEntry(entry4);

		return args ->  cartService.getCarts().forEach(System.out::println);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
