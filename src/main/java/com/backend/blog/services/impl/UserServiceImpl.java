package com.backend.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.blog.entity.User;
import com.backend.blog.exceptions.ResourceNotFoundExceptions;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.repositories.UserRepository;
import com.backend.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.userDtoToUser(userDto);

		User savedUser = userRepo.save(user);

		return this.UserTouserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("User not found", " Id ", id));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());

		User updatedUser = this.userRepo.save(user);

		UserDto updateUser = this.UserTouserDto(updatedUser);

		return updateUser;
	}

	@Override
	public UserDto getUserById(Integer userid) {
		User user = this.userRepo.findById(userid).orElseThrow(() -> new ResourceNotFoundExceptions("User not found", " Id ", userid));

		return this.UserTouserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDto> UserDtos = users.stream().map(user -> this.UserTouserDto(user)).collect(Collectors.toList());
		
		return UserDtos;
	}

	@Override
	public void deleteUser(Integer id) {
		
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("User not found"," Id ",id));
		
		if(user != null) {
			
			this.userRepo.delete(user); 
		}
	}

	public User userDtoToUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class); 
		/*
		 * user.setId(userDto.getId()); user.setName(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setAbout(userDto.getAbout());
		 * user.setPassword(userDto.getPassword());
		 */
		return user;
	}

	public UserDto UserTouserDto(User user) {

		UserDto userDto = this.modelMapper.map(user, UserDto.class); 
		/*
		 * userDto.setId(user.getId()); userDto.setName(user.getName());
		 * userDto.setEmail(user.getEmail()); userDto.setAbout(user.getAbout());
		 * userDto.setPassword(user.getPassword());
		 */
		return userDto;
	}

}
