package com.backend.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments,Long>{

}
