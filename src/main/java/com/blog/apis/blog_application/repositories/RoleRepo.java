package com.blog.apis.blog_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.apis.blog_application.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
