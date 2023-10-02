package com.backend.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JWTTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String requestToken = request.getHeader("Authorization");
		
		System.out.println(requestToken);
		
		String userName = "";
		String token = "";
		
		if(request != null && requestToken.startsWith("Bearer")) {
			
		}else {
			System.out.println("JWT Token dose not being with Bearer");
		}
	}
}
