package com.backend.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

}
