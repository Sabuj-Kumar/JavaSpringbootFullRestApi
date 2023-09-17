package com.backend.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDtio,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			){
		
		PostDto newPostDto = this.postService.createPost(postDtio, userId, categoryId);
		
		return new ResponseEntity<PostDto>(newPostDto,HttpStatus.CREATED);
	}
	
	@PutMapping("post/{postId}")
	public ResponseEntity<PostDto> updatePost(
			@RequestBody PostDto postDtio,
			@PathVariable Integer postId
			){
		
		PostDto newPostDto = this.postService.update(postDtio, postId);
		
		return new ResponseEntity<PostDto>(newPostDto,HttpStatus.OK);
	}
	
	@GetMapping("user/{userId}/post")
	public ResponseEntity<List<PostDto>> getAllPostByUser(
			@PathVariable Integer userId
			){
		
		List<PostDto> posts = this.postService.getAllPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getAllPostByCategory(
			@PathVariable Integer categoryId
			){
		
		List<PostDto> posts = this.postService.getAllPostByCategory(categoryId);
		 
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("posts")
	public ResponseEntity<List<PostDto>> getAllPost(){
		
		List<PostDto> posts = this.postService.getAllPost();
		 
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("posts/{postId}")
	public ResponseEntity<PostDto> getPost(
			@PathVariable Integer postId
			){
		
		PostDto posts = this.postService.getById(postId); 
		 
		return new ResponseEntity<PostDto>(posts,HttpStatus.OK);
	}
	
	@DeleteMapping("posts/{postId}")
	public ApiResponse deletePost(
			@PathVariable Integer postId
			){
		
		this.postService.deletePost(postId); 
		 
		return new ApiResponse("Post Delete Successfull",true);
	}
}
