package com.newrelic.shopjava.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newrelic.shopjava.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUsername(String username);
}
