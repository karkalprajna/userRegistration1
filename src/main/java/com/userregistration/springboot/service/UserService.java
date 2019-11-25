package com.userregistration.springboot.service;

import com.userregistration.springboot.model.User;

public interface UserService {
	User save(User user);
    User findByUsername(String username);
}
