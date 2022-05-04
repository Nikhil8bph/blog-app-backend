package com.devmistri.blog.services;

import java.util.List;

import com.devmistri.blog.entities.User;
import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	ResponseMessage deleteUser(Integer userId);
	ResponseMessage deactivateUser(Integer userId);
	ResponseMessage activateUser(Integer userId);
	
}
