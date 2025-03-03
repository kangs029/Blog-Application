package com.blog.apis.blog_application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.apis.blog_application.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{

    Optional<User> findByEmail(String email);
}
