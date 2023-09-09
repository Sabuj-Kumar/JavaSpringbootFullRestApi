package com.backend.blog.services;

import java.util.List;

import com.backend.blog.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer id);
	UserDto getUserById(Integer userid);
	List<UserDto> getAllUsers();
	void deleteUser(Integer id);
}
