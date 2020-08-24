package com.newrelic.shopjava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newrelic.shopjava.entities.Product;
import com.newrelic.shopjava.entities.Review;
import com.newrelic.shopjava.entities.User;
import com.newrelic.shopjava.repo.UserRepository;
import com.newrelic.shopjava.service.ProductService;
import com.newrelic.shopjava.service.PurchaseService;
import com.newrelic.shopjava.service.ReviewService;
import com.newrelic.shopjava.service.UserService;

@RestController
public class MainController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	// Gets products from database
	@GetMapping("products")
	public List<Product> products(){
		return (List<Product>) productService.list();
	}
	
	//allows user to login into back-end
	@PostMapping("login")
	public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {
		Object token = userService.login(username, password);
		
		return token;
	}
	
	//allows user to sign up into backend
	@PostMapping("signup")
	public Object signup(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("wallet") Long wallet) {
		Object token = userService.signup(username, password, wallet);
		
		return token;
	}
	
	//Gets currently logged in user
	@GetMapping("user")
	public Object getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //Gets username from JWT token
		System.out.println(auth);
		
		User user = userRepo.findUserByUsername(auth.getPrincipal().toString());
		return user;
	}
	
	//allows logged in user to review a product
	@PostMapping("review")
	public Object addReview(@RequestParam("comment") String comment, @RequestParam("rating") Integer rating, @RequestParam("productId") Long productId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepo.findUserByUsername(auth.getPrincipal().toString());
		
		Review review = reviewService.createReview(comment, rating, user, productId);
		return review;
	}
	
	
	//Gets a specific users reviews
	@GetMapping(value = "user/{id}/review")
	public Object getUserReviews(@PathVariable Long id) {
		return userService.getReviews(id);
	}
	
	//Gets a specific products reviews
	@GetMapping(value = "product/{id}/review")
	public Object getProductReviews(@PathVariable Long id) {
		return productService.getReviews(id);
	}
	
	//Allows loggged in user to make a purchase
	@PostMapping("purchase")
	public Object makePurchase(@RequestParam("cart") List<Long> cart) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepo.findUserByUsername(auth.getPrincipal().toString());
		
		return purchaseService.purchase(cart, user);
	}
	
	//Deletes Review
	@DeleteMapping(value = "review/{id}")
	public Object deleteReview(@PathVariable Long id) {
		return reviewService.deleteReview(id);
	}
}
