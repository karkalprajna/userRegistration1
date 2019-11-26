package com.userregistration.springboot.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface SecurityService {
	String findLoggedInUsername();
	UsernamePasswordAuthenticationToken login(String username, String password);
}
