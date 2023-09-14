package com.backend.blog.services;

import java.util.List;

import com.backend.blog.payloads.PostDto;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	PostDto update(PostDto postDto,Integer postId); 
	
	PostDto getById(Integer postId);
	
	void deletePost(Integer postId);
	
	List<PostDto> getAllPost();
	
	List<PostDto> getAllPostByCategory(Integer categoryId);
	
	List<PostDto> getAllPostByUser(Integer userId); 
	
	List<PostDto> serchPosts(String pattern); 
}
