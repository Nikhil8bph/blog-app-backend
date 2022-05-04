package com.devmistri.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devmistri.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
