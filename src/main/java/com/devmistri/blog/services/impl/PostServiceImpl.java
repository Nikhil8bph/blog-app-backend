package com.devmistri.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devmistri.blog.entities.Category;
import com.devmistri.blog.entities.Post;
import com.devmistri.blog.entities.User;
import com.devmistri.blog.exceptions.ResourceNotFoundException;
import com.devmistri.blog.message.response.PostResponse;
import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.PostDto;
import com.devmistri.blog.repositories.CategoryRepo;
import com.devmistri.blog.repositories.PostRepo;
import com.devmistri.blog.repositories.UserRepo;
import com.devmistri.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		Category category=this.categoryRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Category ","Category Id",categoryId));
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","User Id",userId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);		
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);		
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Post Id",postId));
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		post.setContent(postDto.getContent());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public ResponseMessage deletePost(Integer postId) {
		logger.info("Post: Delete post Request received for user id : "+postId);
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
		if(post!=null) {
//			logger.info(post.toString());
			this.postRepo.delete(post);
			logger.info("Post: Post with id %d successfully deleted",postId);
			return new ResponseMessage("Post with id "+postId+" successfully deleted",true);
		}	
		else {
			logger.info("Post: Post with id %d already deleted",postId);
			return new ResponseMessage("User with id "+postId+" already deleted",false);
		}
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort= (sortDir.equalsIgnoreCase(sortDir))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Post Id",postId));		
		PostDto postDtos = this.modelMapper.map(post,PostDto.class);
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ","Category Id",categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","User Id",userId));
		List<Post> posts = this.postRepo.findByUser(user);		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}	

}
