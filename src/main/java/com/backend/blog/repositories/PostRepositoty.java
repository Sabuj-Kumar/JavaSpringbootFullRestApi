package com.backend.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entity.Category;
import com.backend.blog.entity.Post;
import com.backend.blog.entity.User;

public interface PostRepositoty extends JpaRepository<Post,Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
}
