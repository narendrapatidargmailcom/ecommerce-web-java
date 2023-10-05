package com.zosh.ecommerceyoutube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zosh.ecommerceyoutube.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
   public User findByEmail(String email);
}
