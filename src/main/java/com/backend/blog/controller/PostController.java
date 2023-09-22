package com.backend.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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

import com.backend.blog.config.AppConstants;
import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;
import com.backend.blog.services.FileService;
import com.backend.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDtio,
			@PathVariable Long userId,
			@PathVariable Long categoryId
			){
		
		PostDto newPostDto = this.postService.createPost(postDtio, userId, categoryId);
		
		return new ResponseEntity<PostDto>(newPostDto,HttpStatus.CREATED);
	}
	
	@PutMapping("post/{postId}")
	public ResponseEntity<PostDto> updatePost(
			@RequestBody PostDto postDtio,
			@PathVariable Long postId
			){
		
		PostDto newPostDto = this.postService.update(postDtio, postId);
		
		return new ResponseEntity<PostDto>(newPostDto,HttpStatus.OK);
	}
	
	@GetMapping("user/{userId}/post")
	public ResponseEntity<List<PostDto>> getAllPostByUser(
			@PathVariable Long userId
			){
		
		List<PostDto> posts = this.postService.getAllPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getAllPostByCategory(
			@PathVariable Long categoryId
			){
		
		List<PostDto> posts = this.postService.getAllPostByCategory(categoryId);
		 
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value ="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value ="pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value ="sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value ="sortDirection", defaultValue = AppConstants.SORT_DIR,required = false) String sortDirection
			){
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDirection);
		 
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("posts/{postId}")
	public ResponseEntity<PostDto> getPost(
			@PathVariable Long postId
			){
		
		PostDto posts = this.postService.getById(postId); 
		 
		return new ResponseEntity<PostDto>(posts,HttpStatus.OK);
	}
	
	@DeleteMapping("posts/{postId}")
	public ApiResponse deletePost(
			@PathVariable Long postId
			){
		
		this.postService.deletePost(postId); 
		 
		return new ApiResponse("Post Delete Successfull",true); 
	} 
	
	@GetMapping("posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keyword") String keyword
			){
		List<PostDto> lists = this.postService.serchPosts(keyword);
		
		return new ResponseEntity<List<PostDto>>(lists,HttpStatus.OK);
	}
	
	@PostMapping("/post/image/upload/{id}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable("id") Long id
			) throws IOException{
		
		PostDto postDto = this.postService.getById(id);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		
		PostDto updatedPostDto = this.postService.update(postDto, id);
		
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	@GetMapping(value= "/post/image/{imageName}",produces= MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
