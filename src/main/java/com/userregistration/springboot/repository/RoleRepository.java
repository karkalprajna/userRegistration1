package com.userregistration.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userregistration.springboot.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
