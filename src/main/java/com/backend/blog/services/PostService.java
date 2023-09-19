package com.backend.blog.services;

import java.util.List;

import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Long userId,Long categoryId);
	
	PostDto update(PostDto postDto,Long postId); 
	
	PostDto getById(Long postId);
	
	void deletePost(Long postId);
	
	PostResponse getAllPost(Integer pageNumber ,Integer pageSize,String sortBy,String sortDirection);
	
	List<PostDto> getAllPostByCategory(Long categoryId);
	
	List<PostDto> getAllPostByUser(Long userId); 
	
	List<PostDto> serchPosts(String pattern); 
}
