package com.backend.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.blog.entity.Comments;
import com.backend.blog.entity.Post;
import com.backend.blog.exceptions.ResourceNotFoundExceptions;
import com.backend.blog.payloads.CommentDto;
import com.backend.blog.repositories.CommentRepository;
import com.backend.blog.repositories.PostRepositoty;
import com.backend.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepositoty postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundExceptions("Post"," PostId ",postId));
		
		Comments comments = this.modelMapper.map(commentDto,Comments.class);
		
		comments.setPost(post);
		
		Comments saveComment = this.commentRepo.save(comments);
		
		return this.modelMapper.map(saveComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentID) {
		
		Comments comments = this.commentRepo.findById(commentID).orElseThrow(()-> new ResourceNotFoundExceptions("Comments"," CommentId ",commentID));
	    
		this.commentRepo.delete(comments);
	}

}
