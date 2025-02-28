package com.blog.apis.blog_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.apis.blog_application.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{

}
