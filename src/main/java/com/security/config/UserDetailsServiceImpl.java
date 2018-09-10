package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.security.entity.UserEntity;
import com.security.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUserDetails myUserDetails = new MyUserDetails();
		UserEntity user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("未查询到用户：" + username + "信息！");
		}else {
			myUserDetails.setId(user.getId());
			myUserDetails.setUsername(user.getUsername());
			myUserDetails.setPassword(user.getPassword());
			myUserDetails.setRoles(user.getRoles());
		}
		return myUserDetails;
	}
	
}
