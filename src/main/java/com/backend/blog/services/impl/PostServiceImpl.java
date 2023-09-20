package com.backend.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.blog.entity.Category;
import com.backend.blog.entity.Post;
import com.backend.blog.entity.User;
import com.backend.blog.exceptions.ResourceNotFoundExceptions;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;
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
	public PostDto createPost(PostDto postDto,Long userId,Long categoryId) {
		 
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
	public PostDto update(PostDto postDto, Long postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post"," Post Id ",postId)); 
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost =  this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost,PostDto.class); 
	}

	@Override
	public PostDto getById(Long postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post"," Post Id ",postId)); 
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public void deletePost(Long postId) {
		
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post"," Post Id ",postId)); 
		
		this.postRepo.deleteById(post.getId());
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection) {
		
		Sort sort = (sortDirection.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending(); 
		 
		Pageable p = PageRequest.of(pageNumber, pageSize,sort); 
		Page<Post> pageOfPost = this.postRepo.findAll(p);
		List<Post> allPost = pageOfPost.getContent();
		
		List<PostDto> postDtos = allPost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pageOfPost.getNumber());
		postResponse.setPageSize(pageOfPost.getSize());
		postResponse.setTotalElements(pageOfPost.getTotalElements());
		postResponse.setTotalPages(pageOfPost.getTotalPages());
		postResponse.setLastPage(pageOfPost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Long categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExceptions("Category"," Category Id ",categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto>  postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
		
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByUser(Long userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundExceptions("User"," User Id ",userId));
		List<Post>  posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
		
		return postDtos;
	}

	@Override
	public List<PostDto> serchPosts(String pattern) {
		
		List<Post> posts = this.postRepo.findByTitleContaining(pattern); 
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
