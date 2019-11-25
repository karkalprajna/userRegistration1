package com.userregistration.springboot.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.userregistration.springboot.model.User;
import com.userregistration.springboot.repository.RoleRepository;
import com.userregistration.springboot.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	 

	@Override
	public User save(User user) {
		//validate for unique user name
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); 
        user.setRoles(new HashSet<>(roleRepository.findAll()));
		return userRepository.save(user);
		
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
