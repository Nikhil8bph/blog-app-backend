package com.devmistri.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devmistri.blog.entities.Comment;
import com.devmistri.blog.payloads.CommentDto;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
