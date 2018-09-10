package com.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	public UserEntity findByUsername(String username);
	
	@Query(value = "select id,username from UserEntity where 1=1 and username=?1",
		    countQuery = "select count(id) from UserEntity where 1=1 and username=?1",
		    nativeQuery = false)
	Page<UserEntity> find(String username, Pageable pageable);
	
	Page<UserEntity> findAll(Pageable pageable);
}
