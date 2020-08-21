package com.newrelic.shopjava.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.newrelic.shopjava.entities.Review;
import com.newrelic.shopjava.repo.ProductRepository;
import com.newrelic.shopjava.repo.ReviewRepository;
import com.newrelic.shopjava.repo.UserRepository;

public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	public Review createReview(String comment, Integer rating, long userId, long productId) {
		return null;
	}
}
