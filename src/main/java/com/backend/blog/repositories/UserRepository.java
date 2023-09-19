package com.backend.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
