package com.newrelic.shopjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newrelic.shopjava.entities.Review;
import com.newrelic.shopjava.entities.User;
import com.newrelic.shopjava.repo.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.newrelic.shopjava.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Method to process signup
	public HashMap<String, Object> signup(String username, String password, long wallet) {
		User user = new User();
		HashMap<String, Object> map = new HashMap<String, Object>(); //Generates HashMap for response
		
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setWallet(wallet);
		
		if(userRepository.save(user) != null) {
			String token = getJWTToken(user.getUsername());
			map.put("token", token);
			map.put("user_id", user.getId());
			
			return map;
			
			
		}else {
			map.put("Error", "Error submitting user");
			return map;
		}
	}
	
	//Method to process login
	public HashMap<String, Object> login(String username, String password) {
		User user = userRepository.findUserByUsername(username);
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(user == null) {
			map.put("Error", "Login Error");
			return map;
		}else {
			if(passwordEncoder.matches(password, user.getPassword())) {
				String token = getJWTToken(username);
				map.put("token", token);
				map.put("user_id", user.getId());
				
				return map;
			}else {
				map.put("Error", "Login Error");
				return map;
			}
		}
	}
	
	//Method to retreive a users reviews
	public List<Review> getReviews(Long userId){
		User user = userRepository.getOne(userId);
		
		return user.getReviews();
	}
	
	// Method to generate JWT token
	private String getJWTToken(String username) {
		
		String secretKey = SECRET;
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils //assigns authorities
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("Token")
				.setSubject(username) //inserts username into the token
				.claim("authorities",
						grantedAuthorities.stream()
							.map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList())) //inserts authority into token
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact(); //Converts and compacts data into token
		
		return "SHOP " + token;
	}
}
