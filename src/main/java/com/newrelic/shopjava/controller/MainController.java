package com.newrelic.shopjava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newrelic.shopjava.entities.Product;
import com.newrelic.shopjava.entities.User;
import com.newrelic.shopjava.repo.UserRepository;
import com.newrelic.shopjava.service.ProductService;
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
	
//	@Autowired
//	private ReviewService reviewService;
	
	@GetMapping("products")
	public List<Product> products(){
		return (List<Product>) productService.list();
	}
	
	@PostMapping("login")
	public Object login(@RequestParam("username") String username, @RequestParam("password") String password) {
		Object token = userService.login(username, password);
		
		return token;
	}
	
	@PostMapping("signup")
	public Object signup(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("wallet") Long wallet) {
		Object token = userService.signup(username, password, wallet);
		
		return token;
	}
	
	@GetMapping("user")
	public Object getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth);
		
//		User user = userRepo.findUserByUsername(auth.getPrincipal().toString());
//		return user;
		return null;
	}
}
