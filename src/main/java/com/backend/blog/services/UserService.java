package com.backend.blog.services;

import com.backend.blog.payloads.UserDto;
import com.backend.blog.payloads.UserResponse;

public interface UserService {

	UserDto registrationNerUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Long id);
	UserDto getUserById(Long userid);
	UserResponse getAllUsers(Integer pageNumber,Integer pageSize);
	void deleteUser(Long id);
}
