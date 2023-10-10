package com.backend.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.payloads.AuthRequest;
import com.backend.blog.payloads.JwtAuthResponse;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.security.JWTTokenHelper;
import com.backend.blog.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JWTTokenHelper helper;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody AuthRequest request) {

		System.out.println(request.getEmail());
		String token = "";
		try {
			Authentication authentication = manager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
			if (authentication.isAuthenticated()) {
				token = this.helper.generateToken(userDetails);
			}

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}
		JwtAuthResponse response = new JwtAuthResponse();

		response.setToken(token);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler() {
		return "Credentials Invalid !!";
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registrationUser(@RequestBody UserDto useDto){
		
		UserDto registerUser = this.userService.registrationNerUser(useDto);
		
		return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
	}
}
