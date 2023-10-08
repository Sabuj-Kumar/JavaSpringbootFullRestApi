package com.backend.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.backend.blog.security.CustomUserDetailService;
import com.backend.blog.security.JWTAuthenticationFilter;
import com.backend.blog.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	@Lazy
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(userDetailsService());
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(req -> req
		.requestMatchers(mvcMatcherBuilder.pattern("/api/user/"))
		.permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/api/auth/login"))
		.permitAll()
		.anyRequest()  
		.authenticated())
		.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.exceptionHandling(e -> e.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
		.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		return new CustomUserDetailService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
