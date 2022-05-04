package com.devmistri.blog.services;

import java.util.List;

import com.devmistri.blog.entities.Post;
import com.devmistri.blog.message.response.PostResponse;
import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.PostDto;

public interface PostService {

//create 
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
//update 
	PostDto updatePost(PostDto postDto,Integer postId);
	
//delete
	ResponseMessage deletePost(Integer postId);
	
//get All
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

//get Post By Id
	PostDto getPostById(Integer postId);
	
//get All Post By Category
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	
//get All Post By User
	List<PostDto> getAllPostsByUser(Integer userId);

//search Posts
	List<PostDto> searchPosts(String keyword);
}
