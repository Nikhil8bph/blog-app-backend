package com.devmistri.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devmistri.blog.entities.Comment;
import com.devmistri.blog.entities.Post;
import com.devmistri.blog.exceptions.ResourceNotFoundException;
import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.CommentDto;
import com.devmistri.blog.repositories.CommentRepo;
import com.devmistri.blog.repositories.PostRepo;
import com.devmistri.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public ResponseMessage deleteComment(Integer commentId) {
		logger.info("Post: Delete post Request received for user id : "+commentId);
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment Id",commentId));
		if(comment!=null) {
//			logger.info(post.toString());
			this.commentRepo.delete(comment);
			logger.info("Comment: Comment with id %d successfully deleted",commentId);
			return new ResponseMessage("Comment with id "+commentId+" successfully deleted",true);
		}	
		else {
			logger.info("Comment: Comment with id %d already deleted",commentId);
			return new ResponseMessage("Comment with id "+commentId+" already deleted",false);
		}
	}

}
