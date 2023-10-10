package com.backend.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.blog.config.AppConstants;
import com.backend.blog.entity.Role;
import com.backend.blog.entity.User;
import com.backend.blog.exceptions.ResourceNotFoundExceptions;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.payloads.UserResponse;
import com.backend.blog.repositories.RoleRepository;
import com.backend.blog.repositories.UserRepository;
import com.backend.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.userDtoToUser(userDto);

		User savedUser = userRepo.save(user);

		return this.UserTouserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long id) {
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
	public UserDto getUserById(Long userid) {
		User user = this.userRepo.findById(userid).orElseThrow(() -> new ResourceNotFoundExceptions("User not found", " Id ", userid));

		return this.UserTouserDto(user);
	}

	@Override
	public UserResponse getAllUsers(Integer pageNumber,Integer pageSize) {
        
		Pageable p = PageRequest.of(pageNumber, pageSize); 
		Page<User> usersPageList = this.userRepo.findAll(p);
		List<User> users = usersPageList.getContent();
		
		List<UserDto> UserDtos = users.stream().map(user -> this.UserTouserDto(user)).collect(Collectors.toList());
		
		UserResponse userResponse = new UserResponse();
		
		userResponse.setContent(UserDtos); 
		userResponse.setPageNumber(usersPageList.getNumber());
		userResponse.setPageSize(usersPageList.getSize());
		userResponse.setTotalElements(usersPageList.getTotalElements());
		userResponse.setTotalPages(usersPageList.getTotalPages());
		userResponse.setLastPage(usersPageList.isLast());
		
		return userResponse;
	}

	@Override
	public void deleteUser(Long id) {
		
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

	@Override
	public UserDto registrationNerUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto,User.class);
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword())); 
		
		Role role = this.roleRepo.findById(AppConstants.ROLE_ADMIN).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		 
		return this.modelMapper.map(newUser, userDto.getClass());
	}

}
