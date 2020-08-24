package com.newrelic.shopjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrelic.shopjava.entities.Product;
import com.newrelic.shopjava.entities.Review;
import com.newrelic.shopjava.entities.User;
import com.newrelic.shopjava.repo.ProductRepository;
import com.newrelic.shopjava.repo.ReviewRepository;
import com.newrelic.shopjava.repo.UserRepository;

@Service
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	//Method to create review
	public Review createReview(String comment, Integer rating, User user, long productId) {
		Product product = productRepo.getOne(productId); //Gets product from db
		
		Review review = new Review();
		
		review.setComment(comment);
		review.setRating(rating);
		review.setProduct(product);
		review.setUser(user);
		
		return reviewRepo.save(review);
	}
	
	//Method to delete Review
	public String deleteReview(Long id) {
		try {
			
			reviewRepo.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return e.getMessage();
		}
		
	}
}
