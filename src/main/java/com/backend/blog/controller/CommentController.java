package com.backend.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.CommentDto;
import com.backend.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			
			@RequestBody CommentDto commentsDto,
			@PathVariable Long postId
			){
		
		CommentDto createdComments = this.commentService.createComment(commentsDto, postId);
		
		System.out.println(createdComments.getId());
		
		return new ResponseEntity<CommentDto>(createdComments,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> createComment(
			@PathVariable Long commentId
			){
		
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Delete Succesfully",true),HttpStatus.OK);
	}
}
