package com.backend.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{

}
