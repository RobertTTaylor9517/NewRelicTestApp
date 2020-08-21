package com.newrelic.shopjava.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrelic.shopjava.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findByName(String name);
}
