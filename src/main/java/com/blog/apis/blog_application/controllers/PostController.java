package com.blog.apis.blog_application.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.apis.blog_application.configs.AppConstants;
import com.blog.apis.blog_application.payloads.ApiResponse;
import com.blog.apis.blog_application.payloads.PostDTO;
import com.blog.apis.blog_application.payloads.PostResponse;
import com.blog.apis.blog_application.services.FileService;
import com.blog.apis.blog_application.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(
        @Valid
        @RequestBody PostDTO postDTO,
        @PathVariable Integer userId,
        @PathVariable Integer categoryId) {
        
        PostDTO createPost= this.postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<PostDTO>(createPost,HttpStatus.CREATED);
    }
    
    //get post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByUser(
        @Valid
        @PathVariable Integer userId) {
        
        List<PostDTO> posts= this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
    }

    //get post by Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByCategory(
        @Valid
        @PathVariable Integer categoryId) {
        
        List<PostDTO> posts= this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
    }

    //get All Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
        @RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
        @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
        @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir) {
        
        PostResponse posts= this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    //get post by user
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostById(
        @Valid
        @PathVariable Integer postId) {
        
        PostDTO post= this.postService.getPostById(postId);
        return new ResponseEntity<PostDTO>(post,HttpStatus.OK);
    }

    //update Post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePostById(
        @Valid
        @RequestBody PostDTO postDTO,
        @PathVariable Integer postId) {
        
        PostDTO post= this.postService.upadatePost(postDTO,postId);
        return new ResponseEntity<PostDTO>(post,HttpStatus.OK);
    }
    

    //delete post by id
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(
        @Valid
        @PathVariable Integer postId) {
        
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted Succesfully",true),HttpStatus.OK);
    }

    //search
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(
        @PathVariable("keyword") String keyword){

        List<PostDTO> posts= this.postService.serachPosts(keyword);
        return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
        @RequestParam("image") MultipartFile image,
        @PathVariable("postId") Integer postId) throws IOException{

        PostDTO postDto = this.postService.getPostById(postId);

        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);

        PostDTO updatedPostDto = this.postService.upadatePost(postDto, postId);

        return new ResponseEntity<PostDTO>(updatedPostDto,HttpStatus.OK);
    }

    //method to serve files (http://localhost:9090/api/post/images/b113fd4a-39e5-47c1-a60e-30f436459fe4.jpg)
    @GetMapping(value = "post/images/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response) throws IOException{

        InputStream resource = this.fileService.getResource(path, imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
    
    
}
