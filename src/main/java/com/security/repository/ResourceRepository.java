package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.ResourceEntity;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

	
}
