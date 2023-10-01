package com.backend.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);
}
