package com.newrelic.shopjava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrelic.shopjava.entities.Product;
import com.newrelic.shopjava.entities.User;
import com.newrelic.shopjava.repo.ProductRepository;
import com.newrelic.shopjava.repo.UserRepository;

@Service
public class PurchaseService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	public String purchase(List<Long> cart, User user) {
		try {
			float total = (float) cart.stream().mapToDouble(e -> processProduct(e)).sum();
			
			float currentWallet = user.getWallet();
			user.setWallet(currentWallet - total);
			
			userRepo.save(user);
			
			return "Success";
		} catch(Exception e) {
			return e.getMessage();
		}
		
	}
	
	private Double processProduct(Long prodId) {
		Product product = productRepo.getOne(prodId);
		
		int currentQuantity = product.getQuantity();
		
		try {
			product.setQuantity(currentQuantity - 1);
			
			productRepo.save(product);
			
			return product.getPrice();
			
		} catch(Exception e) {
			return null;
		}
		
	}
}
