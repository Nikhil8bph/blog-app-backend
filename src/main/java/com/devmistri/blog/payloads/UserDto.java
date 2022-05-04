package com.devmistri.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=3, message="Username must be more than 3 characters")
	private String name;
	
	@Email(message="Email id is not valid")
	private String email;
	
	@NotEmpty
	@Size(min=8,max=12,message="password must be min of 8 and max 12 characters!!")
	private String password;
	
	@NotEmpty
	private String about;
	
	
	private boolean userActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public boolean isUserActive() {
		return userActive;
	}
	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", userActive=" + userActive + "]";
	}
	
}
