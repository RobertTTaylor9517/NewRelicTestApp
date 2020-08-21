package com.newrelic.shopjava.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrelic.shopjava.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
}
