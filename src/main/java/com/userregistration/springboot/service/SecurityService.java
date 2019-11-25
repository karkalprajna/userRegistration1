package com.userregistration.springboot.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface SecurityService {
	UsernamePasswordAuthenticationToken login(String username, String password);
}
