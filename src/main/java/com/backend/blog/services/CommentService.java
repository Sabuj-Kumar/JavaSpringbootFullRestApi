package com.backend.blog.services;

import com.backend.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Long postId);
	
	void deleteComment(Long commentID);
}
