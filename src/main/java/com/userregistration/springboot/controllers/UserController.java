package com.userregistration.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userregistration.springboot.model.Role;
import com.userregistration.springboot.model.User;
import com.userregistration.springboot.service.RoleService;
import com.userregistration.springboot.service.SecurityService;
import com.userregistration.springboot.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SecurityService securityService;

	@PostMapping("/role")
	public ResponseEntity<Role> createRoles(@RequestBody Role role) {
		return ResponseEntity.ok().body(roleService.save(role));
	}

	@PostMapping("/registration")
	public ResponseEntity<User> createUser(@RequestBody User user) { 
		return ResponseEntity.ok().body(userService.save(user));
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {

		return securityService.login(username, password).toString();
	}

	// logout
	@GetMapping("/logout")
	public String logout() {
		// sessionStorage.removeItem('username');
		return "loged out";

	}

	// forgot password
	// after login we should be able to go get registered user details , how to
	// access the end point only after login.
	@GetMapping("/registration/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) {
		return ResponseEntity.ok().body(userService.findByUsername(username));
	}
}
