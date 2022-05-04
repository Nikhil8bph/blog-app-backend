package com.devmistri.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devmistri.blog.config.Constants;
import com.devmistri.blog.message.response.PostResponse;
import com.devmistri.blog.message.response.ResponseMessage;
import com.devmistri.blog.payloads.PostDto;
import com.devmistri.blog.services.FileService;
import com.devmistri.blog.services.PostService;

@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> postsByUser = this.postService.getAllPostsByUser(userId);		
		return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);	
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> postsByCategory = this.postService.getAllPostsByCategory(categoryId);		
		return new ResponseEntity<List<PostDto>>(postsByCategory,HttpStatus.OK);	
	}
	
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = Constants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = Constants.PAGE_Size,required = false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = Constants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = Constants.SORT_DIR,required=false) String sortDir
			){
		PostResponse posts = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);		
		return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);	
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postsByCategory = this.postService.getPostById(postId);		
		return new ResponseEntity<PostDto>(postsByCategory,HttpStatus.OK);	
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ResponseMessage> deletePostById(@PathVariable Integer postId){
		ResponseMessage responseMessage = this.postService.deletePost(postId);	
		return new ResponseEntity<ResponseMessage>(responseMessage,HttpStatus.OK);	
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keywords){
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		this.postService.updatePost(postDto, postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping(value="/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
}
