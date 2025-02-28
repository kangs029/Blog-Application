package com.blog.apis.blog_application.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.apis.blog_application.entities.User;
import com.blog.apis.blog_application.entities.Category;
import com.blog.apis.blog_application.entities.Post;
import com.blog.apis.blog_application.exceptions.ResourceNotFoundException;
import com.blog.apis.blog_application.payloads.PostDTO;
import com.blog.apis.blog_application.payloads.PostResponse;
import com.blog.apis.blog_application.repositories.CategoryRepo;
import com.blog.apis.blog_application.repositories.PostRepo;
import com.blog.apis.blog_application.repositories.UserRepo;
import com.blog.apis.blog_application.services.PostService;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId) {
        User user = this.userRepo.findById(userId)
                        .orElseThrow(()->new ResourceNotFoundException("user", "userId", userId));
        
        Category category = this.categoryRepo.findById(categoryId)
                        .orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));

        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        return this.modelMapper.map(this.postRepo.save(post),PostDTO.class);
    }

    @Override
    public PostDTO upadatePost(PostDTO postDto, Integer postId) {
        Optional<Post> post= this.postRepo.findById(postId);
        if(post.isEmpty()){
            throw new ResourceNotFoundException("Post","PostId",postId);
        }
        Post posT=post.get();

        posT.setContent(postDto.getContent());
        posT.setTitle(postDto.getTitle());
        posT.setImageName(postDto.getImageName());

        return this.modelMapper.map(this.postRepo.save(posT),PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Optional<Post> post= this.postRepo.findById(postId);
        if(post.isEmpty()){
            throw new ResourceNotFoundException("Post","PostId",postId);
        }
        this.postRepo.delete(post.get());
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        if(sortDir.equals("asc")){

        }else{

        }

        Pageable pageable=PageRequest.of(pageNumber, pageSize,Sort.by(sortBy)); 
        Page<Post> pagePost = this.postRepo.findAll(pageable);
        List<Post>posts= pagePost.getContent(); 

        // List<Post>posts= this.postRepo.findAll(); 

       List<PostDTO> postDTOs = posts.stream()
                   .map((post)->this.modelMapper.map(post,PostDTO.class))
                   .collect(Collectors.toList());

        PostResponse postResponse= new PostResponse();
        postResponse.setContent(postDTOs);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Optional<Post> post= this.postRepo.findById(postId);
        if(post.isEmpty()){
            throw new ResourceNotFoundException("Post","PostId",postId);
        }
        return this.modelMapper.map(post.get(),PostDTO.class) ;
    }

    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {
        Optional<Category> categoryOp = this.categoryRepo.findById(categoryId);
        if(categoryOp.isEmpty()){
            throw new ResourceNotFoundException("Category","categoryId",categoryId);
        }
        Category category=categoryOp.get();

        List<Post> posts = this.postRepo.findByCategory(category);
        
        return posts.stream()
             .map(post->this.modelMapper.map(post,PostDTO.class))
             .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByUser(Integer userId) {
        Optional<User> userOp = this.userRepo.findById(userId);
        if(userOp.isEmpty()){
            throw new ResourceNotFoundException("User","userId",userId);
        }
        User user=userOp.get();

        List<Post> posts = this.postRepo.findByUser(user);
        
        return posts.stream()
             .map(post->this.modelMapper.map(post,PostDTO.class))
             .collect(Collectors.toList());
    }

    @Override
    //using JPA method
    // public List<PostDTO> serachPosts(String keyword) {

    //     List<Post> posts = this.postRepo.findByTitleContaining(keyword);
    //     return posts.stream()
    //          .map(post->this.modelMapper.map(post,PostDTO.class))
    //          .collect(Collectors.toList());
    // }

    //using self created method
    public List<PostDTO> serachPosts(String keyword) {

        List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
        return posts.stream()
             .map(post->this.modelMapper.map(post,PostDTO.class))
             .collect(Collectors.toList());
    }

}
