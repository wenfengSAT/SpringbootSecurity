package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	
}
