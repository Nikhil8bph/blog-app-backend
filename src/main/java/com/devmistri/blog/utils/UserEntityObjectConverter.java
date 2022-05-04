package com.devmistri.blog.utils;

import com.devmistri.blog.entities.User;
import com.devmistri.blog.payloads.UserDto;

public class UserEntityObjectConverter {
	public static User dtoToUser(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		user.setUserActive(userDto.isUserActive());
		return user;
	}
	
	public static UserDto userToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());
		userDto.setUserActive(user.isUserActive());
		return userDto;
	}
}
