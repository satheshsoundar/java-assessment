package com.java.assessment.product;

import com.java.assessment.product.model.Product;
import com.java.assessment.product.service.ProductService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;

@ComponentScan("com.java.assessment.product.*")
@SpringBootApplication
@EntityScan("com.java.assessment.product.*")
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = { "com.java.assessment.product.*" })
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Bean
	ApplicationRunner init(ProductService productService ){

		Product product = new Product();
		product.setId(1l);
		product.setDescription("Soap");
		product.setPrice(new BigDecimal(10.00) );
		product.setQuantity(10);
		product.setName("Dove");
		product.setCode("Product-1234");

		Product product1 = new Product();
		product1.setId(2l);
		product1.setDescription("Soap");
		product1.setPrice(new BigDecimal(10.00));
		product1.setQuantity(25);
		product1.setName("Axe");
		product1.setCode("Product-2345");

		Product product2 = new Product();
		product2.setId(3l);
		product2.setDescription("Soap");
		product2.setPrice(new BigDecimal(25.00));
		product2.setQuantity(10);
		product2.setName("Brooklyn");
		product2.setCode("Product-3456");

		Product product3 = new Product();
		product3.setId(4l);
		product3.setDescription("Cream");
		product3.setPrice(new BigDecimal(25.00));
		product3.setQuantity(20);
		product3.setName("Aqua");
		product3.setCode("Product-4567");

		Product product4 = new Product();
		product4.setId(5l);
		product4.setDescription("Cream");
		product4.setPrice(new BigDecimal(10.00));
		product4.setQuantity(30);
		product4.setName("La Mer");
		product4.setCode("Product-5678");

		productService.save(product);
		productService.save(product1);
		productService.save(product2);
		productService.save(product3);
		productService.save(product4);
		return args ->  productService.findAllProducts().forEach(System.out::println);
	}
}
