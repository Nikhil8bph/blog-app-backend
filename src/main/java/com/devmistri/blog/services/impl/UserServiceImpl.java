package com.devmistri.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devmistri.blog.entities.User;
import com.devmistri.blog.exceptions.ResourceNotFoundException;
import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.UserDto;
import com.devmistri.blog.repositories.UserRepo;
import com.devmistri.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		logger.info("User: create request received");
		userDto.setUserActive(true);		
		User user= this.modelMapper.map(userDto,User.class);
		User savedUser=this.userRepo.save(user);
		logger.info(String.valueOf(userDto));
		logger.info(savedUser.toString());
		logger.info("User: created successfully with userId : "+savedUser.getId());
		return this.modelMapper.map(savedUser,UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		logger.info("update request received for userId : "+userId);
		logger.info("User: "+String.valueOf(userDto));
		logger.info("User: "+user.toString());
		if((userDto.getAbout()!=user.getAbout() || userDto.getEmail()!=user.getEmail() || userDto.getName()!=user.getName() || userDto.getPassword()!=user.getPassword()) && user.isUserActive()==true)
		{
			user.setName(userDto.getEmail());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setAbout(userDto.getAbout());
			User updatedUser=this.userRepo.save(user);
			logger.info("User: update request processed successfully for userId : "+userId);
			return this.modelMapper.map(updatedUser,UserDto.class); 
			
		}
		else {
			return userDto;
		}			
	}

	@Override
	public UserDto getUserById(Integer userId) {
		logger.info("User: get User Request received for user id : "+userId);
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		return this.modelMapper.map(user,UserDto.class); 
	}

	@Override
	public List<UserDto> getAllUsers() {
		logger.info("User: Get List of users request received");
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().filter(user->user.isUserActive()==true).map(user->this.modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
		logger.info("User: List of all users : "+userDtos);
		return userDtos;
	}

	@Override
	public ResponseMessage deleteUser(Integer userId) {
		logger.info("User: Delete User Request received for user id : "+userId);
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		if(user.isUserActive()==true) {
			logger.info(user.toString());
			this.userRepo.delete(user);
			logger.info("User: User with id %d successfully deleted",userId);
			return new ResponseMessage("User with id "+userId+" successfully deleted",true);
		}	
		else {
			logger.info("User: User with id %d already deleted",userId);
			return new ResponseMessage("User with id "+userId+" already deleted",false);
		}
	}
	
	@Override
	public ResponseMessage deactivateUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		if(user.isUserActive()==true) {
			user.setUserActive(false);
			this.userRepo.save(user);
			logger.info("User with id %d successfully deactivated",userId);
			logger.info(user.toString());
			return new ResponseMessage("User with id "+userId+" successfully deactivated",true);
		}	
		else {
			logger.info("User with id %d already deactive",userId);
			return new ResponseMessage("User with id "+userId+" already deactive",false);
		}		
	}
	
	@Override
	public ResponseMessage activateUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		if(user.isUserActive()==false) {
			user.setUserActive(true);
			this.userRepo.save(user);
			logger.info("User with id "+userId+" successfully activated");
			logger.info(user.toString());
			return new ResponseMessage("User with id "+userId+" successfully activated",true);
		}	
		else {
			logger.info("User with id %d already active",userId);
			return new ResponseMessage("User with id "+userId+" already active",false);
		}
	}
	
}
