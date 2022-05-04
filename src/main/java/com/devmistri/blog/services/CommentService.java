package com.devmistri.blog.services;

import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	ResponseMessage deleteComment(Integer commentId);
}
