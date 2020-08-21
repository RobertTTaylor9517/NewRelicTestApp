package com.newrelic.shopjava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrelic.shopjava.entities.Product;
import com.newrelic.shopjava.entities.Review;
import com.newrelic.shopjava.repo.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	public Iterable<Product> list(){
		return productRepo.findAll();
	}
	
	public Iterable<Product> save(List<Product> products) {
		return productRepo.saveAll(products);
	}
	
	public List<Review> getReviews(Long id){
		Product product = productRepo.getOne(id);
		
		return product.getReviews();
	}
}
