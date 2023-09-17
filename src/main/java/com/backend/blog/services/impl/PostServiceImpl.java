package com.backend.blog.services.impl;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.blog.entity.Category;
import com.backend.blog.entity.Post;
import com.backend.blog.entity.User;
import com.backend.blog.exceptions.ResourceNotFoundExceptions;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.repositories.CategoryRepository;
import com.backend.blog.repositories.PostRepositoty;
import com.backend.blog.repositories.UserRepository;
import com.backend.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepositoty postRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		 
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundExceptions("User"," User Id ",userId));                    
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExceptions("Category"," Category Id ",categoryId));
		Post post = this.modelMapper.map(postDto,Post.class);
		
		post.setImageName("default.png"); 
		post.setUser(user);
		post.setCategory(category);
		post.setAddedDate(new Date()); 
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto update(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post"," Post Id ",postId)); 
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost =  this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost,PostDto.class); 
	}

	@Override
	public PostDto getById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post"," Post Id ",postId)); 
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post"," Post Id ",postId)); 
		
		this.postRepo.deleteById(post.getId());
	}

	@Override
	public List<PostDto> getAllPost() {
		List<Post> allPost = this.postRepo.findAll();
		
		List<PostDto> postDtos = allPost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExceptions("Category"," Category Id ",categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto>  postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
		
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundExceptions("User"," User Id ",userId));
		List<Post>  posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
		
		return postDtos;
	}

	@Override
	public List<PostDto> serchPosts(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
