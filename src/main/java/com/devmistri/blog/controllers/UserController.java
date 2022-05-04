package com.devmistri.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.validation.Valid;

import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.UserDto;
import com.devmistri.blog.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;	
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){		
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userid") Integer userId){		
		UserDto updateUserDto = this.userService.updateUser(userDto,userId);
		return new ResponseEntity<>(updateUserDto,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUsersList(){
		List<UserDto> userDtos = this.userService.getAllUsers();
		return new ResponseEntity<>(userDtos,HttpStatus.OK);		
	}
	
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getOneUser(@PathVariable("userid") Integer userId){
		UserDto userDto = this.userService.getUserById(userId);
		return new ResponseEntity<>(userDto,HttpStatus.OK);		
	}
	
	@DeleteMapping("/{userid}")
	public ResponseEntity<ResponseMessage> deleteOneUser(@PathVariable("userid") Integer userId){
		ResponseMessage responseMessage = this.userService.deleteUser(userId);
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);		
	}
	
	@GetMapping("/deactivate/{userid}")
	public ResponseEntity<ResponseMessage> deactivateUser(@PathVariable("userid") Integer userId){
		ResponseMessage responseMessage = this.userService.deactivateUser(userId);
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);		
	}
	
	@GetMapping("/activate/{userid}")
	public ResponseEntity<ResponseMessage> activateUser(@PathVariable("userid") Integer userId){
		ResponseMessage responseMessage = this.userService.activateUser(userId);
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);		
	}
	
	
}
