package com.backend.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.blog.entity.User;
import com.backend.blog.exceptions.ResourceNotFoundExceptions;
import com.backend.blog.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundExceptions(" User"," Email "+username,0));
		
		return user;
	}

}
