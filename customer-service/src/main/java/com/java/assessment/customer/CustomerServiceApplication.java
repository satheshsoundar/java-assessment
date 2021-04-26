package com.java.assessment.customer;

import com.java.assessment.customer.beans.Address;
import com.java.assessment.customer.beans.Customer;
import com.java.assessment.customer.beans.CustomerCart;
import com.java.assessment.customer.service.AddressService;
import com.java.assessment.customer.service.CustomerCartService;
import com.java.assessment.customer.service.CustomerService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@ComponentScan("com.java.assessment.customer.*")
@SpringBootApplication
@EntityScan("com.java.assessment.customer.*")
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = { "com.java.assessment.customer.*" })
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner init(CustomerService customerService, CustomerCartService customerCartService, AddressService addressService) {

		Address address1 = new Address();
		address1.setCountry("Germany");
		address1.setName("Iron Man");
		address1.setPostalCode("11111");
		address1.setStreetName("Avenger street");
		address1.setStreetNumber("11");

		Customer customer1 = new Customer();
		customer1.setName( "Sathesh" );
		customer1.setBillingAddress(address1);
		customer1.setShippingAddress(address1);
		customer1.setAddressList(Arrays.asList(address1));

		Address address2 = new Address();
		address2.setCountry("Germany");
		address2.setName("Super Man");
		address2.setPostalCode("11111");
		address2.setStreetName("DC street");
		address2.setStreetNumber("1111");

		Customer customer2 = new Customer();
		customer2.setName( "Hema" );
		customer2.setBillingAddress(address2);
		customer2.setShippingAddress(address2);
		customer2.setAddressList(Arrays.asList(address2));

		Customer customer3 = new Customer();
		customer3.setName( "Menmozhi" );
		customer3.setBillingAddress(address1);
		customer3.setShippingAddress(address1);
		customer3.setAddressList(Arrays.asList(address1));


		addressService.saveAddress(address1);
		addressService.saveAddress(address2);

		customerService.saveCustomer( customer1 );
		customerService.saveCustomer( customer2 );
		customerService.saveCustomer( customer3 );

		return args ->  customerService.getCustomers().forEach(System.out::println);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
