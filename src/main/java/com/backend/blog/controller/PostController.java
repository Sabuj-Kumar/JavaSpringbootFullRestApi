package com.backend.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
