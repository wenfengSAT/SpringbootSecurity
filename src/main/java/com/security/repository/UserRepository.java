package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.security.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	public UserEntity findByUsername(String username);
}
