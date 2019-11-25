package com.userregistration.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userregistration.springboot.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);

}
