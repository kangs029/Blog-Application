package com.blog.apis.blog_application.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.apis.blog_application.entities.Category;
import com.blog.apis.blog_application.entities.Post;
import com.blog.apis.blog_application.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer>{
    
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    //JPA
    //List<Post> findByTitleContaining(String title);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);
}
