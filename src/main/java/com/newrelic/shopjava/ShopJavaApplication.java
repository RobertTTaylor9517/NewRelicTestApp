package com.newrelic.shopjava;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newrelic.shopjava.service.ProductService;
import com.newrelic.shopjava.entities.Product;
import com.newrelic.shopjava.security.JWTAuthFilter;



@SpringBootApplication
public class ShopJavaApplication{

	public static void main(String[] args) {
		SpringApplication.run(ShopJavaApplication.class, args);
	}
	
	@EnableWebSecurity
	@Configuration
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			http.csrf().disable()
				.addFilterAfter(new JWTAuthFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/products").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/user/{id}/review").permitAll()
				.antMatchers("/product/{id}/review").permitAll()
				.anyRequest().authenticated();
					
		}
	}
	
	
	@Bean
	CommandLineRunner runner(ProductService productService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			TypeReference<List<Product>> typeRef = new TypeReference<List<Product>>() {};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/db/products.json");
			try {
				List<Product> products = mapper.readValue(inputStream, typeRef);
				productService.save(products);
				System.out.println("Saved");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
