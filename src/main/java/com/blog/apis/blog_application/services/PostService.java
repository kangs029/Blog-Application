package com.blog.apis.blog_application.services;

import java.util.List;

import com.blog.apis.blog_application.payloads.PostDTO;
import com.blog.apis.blog_application.payloads.PostResponse;

public interface PostService {
    //create
    PostDTO createPost(PostDTO postDTO,Integer userId, Integer categoryId);

    //update
    PostDTO upadatePost(PostDTO postDto,Integer postId);

    //delete
    void deletePost(Integer postId);

    //get All posts
    // List<PostDTO> getAllPost();
    // List<PostDTO> getAllPost(Integer pageNumber,Integer pageSize);
    // PostResponse getAllPost(Integer pageNumber,Integer pageSize);
    PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

    //get single post
    PostDTO getPostById(Integer postId);

    //get all post by category
    List<PostDTO> getPostsByCategory(Integer categoryId);

    //get all post By user
    List<PostDTO> getPostsByUser(Integer userId);

    //search posts
    List<PostDTO> serachPosts(String keyword);
}
