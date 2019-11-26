package com.userregistration.springboot.service;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
	    }
		return user;
	}
	
	@Override
	public String forgotpassword(String userName) {
		User user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException(userName);
	    }
		String token = UUID.randomUUID().toString();
		createPasswordResetTokenForUser(user,token);
		return token;
	}
	
	public void createPasswordResetTokenForUser(User user, String token) {
		user.setPassword(bCryptPasswordEncoder.encode(token));
		userRepository.save(user);
	}

}
