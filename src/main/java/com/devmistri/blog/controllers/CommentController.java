package com.devmistri.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.CommentDto;
import com.devmistri.blog.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId){
		CommentDto createComment = this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ResponseMessage> deleteComment(@PathVariable Integer commentId){
		ResponseMessage responseMessage = this.commentService.deleteComment(commentId);
		return new ResponseEntity<ResponseMessage>(responseMessage,HttpStatus.OK);
	}
}
